图片加载这种实现繁琐，可复用性又极强的东西，自然是选择使用图片加载框架来快速实现。
像是Android-Universal-Image-Loader、Glide、Picasso、Fresco之类， 但是这时候的烦恼在于，这么多图片加载框架到底谁最实用？

 1. 有说Fresco，因为支持WebP，还是用了NDK来加载图片，减少JavaHeap的使用
 2. 有Picasso，简洁高效
 3. 有说Glide，Picasso升级，可以加载Gif，在Picasso基础上扩展了很多方法
 4. ImageLoader  使用最广，因为出来最早，可惜没人维护了
 
算了，毕竟**实践才是检验真理的唯一标准**，不如自己写代码测试下，正好还能学习下各个框架怎么使用的。
本文到底准备做什么，一图胜千言
![这里写图片描述](http://img.blog.csdn.net/20160706152847155)
#回合一：测试网络图片加载速度

##如何获取图片加载所用时间
###**尝试修改框架代码**
我开始尝试，修改图片加载框架的代码，在初始化的时候记录下系统时间t1。然后当图片加载成功后，使用当前系统时间t2,t2-t1就是框架加载所消耗的时间。但是实际去做的时候，在改完Picasso源码后，发现这种实现可行性太弱，相当于每个框架我都需要去修改代码，而且后面的框架实现都比Picasso要复杂，像Fresco这种，代码繁多，修改起来很麻烦。
###**利用框架本身特性-占位图和错误显示图**
换一种思路，所有的框架不都支持占位符，和出错操作吗？也就是在图片加载过程中可以先设置一张占位图片，如果加载出错可以放置一张出错图片。

```
        Glide.with(getContext())
                .load(url)
                .placeholder(Drawables.sPlaceholderDrawable) //初始化显示
                .error(Drawables.sErrorDrawable)  //加载失误显示
                .into(mImageView);
```
也就是说我们可以根据，最终加载出来的图片资源来判定加载成功与否。
![这里写图片描述](http://img.blog.csdn.net/20160705185255391)。
那么怎么获得这个时间？
图片最终显示在ImageView上，我们传的给框架的又是`Drawable`类型的图，那么框架最终肯定会调用ImageView的`setImageDrawable()`方法。可以通过重写ImageView的这个方法，来实现，主要看` public void setImageDrawable(Drawable drawable)`里面的代码

```
public class WatchImageView extends ImageView implements WatchInterface {
    private final WatchImpl mWatcher;

    public WatchImageView(Context context) {
        super(context);
        //设置图片填充样式为按比例填满控件
        setScaleType(ScaleType.CENTER_CROP);
        mWatcher = new WatchImpl(this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制结果会用到，通过mWatcher转发
        mWatcher.onDraw(canvas);
    }

//    禁用这个方法，防止框架使用它显示图片，影响我们测试显示
    @Override
    public void setImageURI(Uri uri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void initWatcher(String tag, WatchListener watchListener) {
        mWatcher.init(tag, watchListener);
        mWatcher.onStart(); //初始化
    }
    
//具体实现
    @Override
    public void setImageDrawable(Drawable drawable) {

        Preconditions.checkNotNull(drawable);
        if (drawable == Drawables.sPlaceholderDrawable) {
            //不再这里调用，在初始化加载器，Glide.build()实现调用更好
        } else if (drawable == Drawables.sErrorDrawable) {
            mWatcher.onFailure();//加载失败
        } else {
            mWatcher.onSuccess();//加载成功
        }
        super.setImageDrawable(drawable);
    }
}
```

这里的WatchInterface用来初始化，监视器用

```
public interface WatchInterface {
    void initWatcher(final String tag, WatchListener watchListener);
}
```
请求次数统计放在`WatchListener `里实现。
###**注意：一定要设置占位图和出错图**
因为最终我们是通过判断，当前ImageView显示的具体是哪一张Drawable来判定加载失败，加载成功，还是取消加载。所以一定要设置，而且限定占位图一定要是`R.drawable.placeholder`
![这里写图片描述](http://img.blog.csdn.net/20160706002941609)
出错图一定要是`R.drawable.error`
![这里写图片描述](http://img.blog.csdn.net/20160706003015094)
为了方便使用，进行了简单封装

```
public class Drawables {
    public static Drawable sPlaceholderDrawable;
    public static Drawable sErrorDrawable;

    private Drawables() {
    }

    public static void init(final Resources resources) {
        if (sPlaceholderDrawable == null) {
            sPlaceholderDrawable = resources.getDrawable(R.drawable.placeholder);
        }
        if (sErrorDrawable == null) {
            sErrorDrawable = resources.getDrawable(R.drawable.error);
        }
    }
}
```
使用：

 1.Glide
 

```
        Glide.with(getContext())
                .load(url)
                .placeholder(Drawables.sPlaceholderDrawable)
                .error(Drawables.sErrorDrawable)
```

2.Picasso  大同小异

```
        mPicasso.load(url)
                .placeholder(Drawables.sPlaceholderDrawable)
                .error(Drawables.sErrorDrawable)
```
3.ImageLoader

```
            mImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(Drawables.sPlaceholderDrawable)
                    .showImageOnFail(Drawables.sErrorDrawable)
```
4.Fresco

```
        GenericDraweeHierarchy genericDraweeHierarchy = new GenericDraweeHierarchyBuilder(getContext().getResources())
                .setPlaceholderImage(Drawables.sPlaceholderDrawable)
                .setFailureImage(Drawables.sErrorDrawable)
```
##测试注意事项
为了保证，测试的公平性，保证框架使用的加载环境一致，对每个框架运行来说

 1. 加载图片的地址一致，加载顺序一致
 2. 禁止使用内存缓存，硬盘缓存，只能通过网络获取图片
 3. 测试的软硬件环境一致,即:使用同一个手机测试，而且每次使用一个框架加载后，清空内存再使用另一个框架测试

##设置不使用缓存
因为是测试网络图片加载能力，内存缓存和磁盘缓存应该被禁止
###**Glide不使用缓存**
直接提供了
```
                .skipMemoryCache(true) //不使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) //不使用硬盘缓存
```

###**Fresco不使用缓存**
Fresco麻烦一点，找了好久没找到这个方法，**如果有知道的朋友，烦请告诉我下**
换一种思路，我直接设置他的内存缓存为空间为0，磁盘缓存空间也为0，即可


###**Picasco不使用缓存**
一句话简单快捷
```
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//不使用内存缓存
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)//不使用磁盘缓存
```
###**ImageLoader不使用缓存**

```
                    .cacheInMemory(false) //不使用内存缓存
                    .cacheOnDisk(false)  //不使用硬盘缓存
```

 
##最终效果
正常网络环境下，几个框架表现都差不多（你也可以使用本文的Demo，应该比这个速度快多了，我在家里测试基本一张图片在1000-2000ms左右。）
但是，这里测试使用的网络比较慢
|名称| 统计次数| 平均时间(ms)  |
| :------------: |:-------------:| :-----:|
| Fresco| 72 | 3877ms |
|  Picasso | 100|   3976ms |
| ImageLoader | 100|    4402ms |
| Glide | 100|    4800ms |
结果出乎意料，Glide居然性能最差。
###**Fresco的Native Heap**
记得前面，我们把硬盘缓存和内存缓存的大小都设置为0，但是Fresco还有一个Native层缓存。
重现步骤：

 1. Fresco正常加载图片，退出App
 2. 关闭网络，清理App后台占用资源
 3. 再次打开，使用Fresco再次加载，居然会有一些偶发的加载成功
![这里写图片描述](http://img.blog.csdn.net/20160706163333305)
看加载时间73ms，这明显来自缓存
###**Glide经常加载失败**
在网络正常的时候，这个现象很少见。但是切换到网络不那么好，这个Glide加载失败的情况频发。
仔细观察加载时间，网络访问2645ms就回调显示失败，关键是同时加载的图片中，还有存在10000+ms加载成功的情况，这里让我觉得非常困惑
 ![这里写图片描述](http://img.blog.csdn.net/20160706141835536)


Glide在使用本身网络加载的时候，在网络环境不好的时候，加载速度慢，加载策略奇葩。（后序：在用Glide中内置OkHttp作为HttpClient后，这个问题得以修正，会在后面的Blog写出来）。



 
#回合二：缓存加载速度

##前提

 1. 保证每个框架使用的内存缓存，磁盘缓存都是一致的。（内存缓存为系统可以缓存的1/4，磁盘缓存都是50M）
 2. 保证图片都已缓存到本地，改变图片加载顺序让其随机出现
 3. 测试环境保持一致
 
##修改缓存大小
先把配置信息写入文件

```
public class ConfigConstants {
    public static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
    public static final int MAX_CACHE_MEMORY_SIZE = MAX_HEAP_SIZE / 4;
    public static final int MAX_CACHE_DISK_SIZE = 50 * 1024 * 1024;
}
```

###Glide
Glide修改配置信息的方式，是通过XML结点元素
定义
```
public class GlideConfigModule implements GlideModule {
    public final static String TAG = "GlideConfigModule";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, ConfigConstants.MAX_CACHE_DISK_SIZE));
        builder.setMemoryCache(new LruResourceCache(ConfigConstants.MAX_CACHE_MEMORY_SIZE));
        builder.setBitmapPool(new LruBitmapPool(ConfigConstants.MAX_CACHE_MEMORY_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}
```
然后再AndroidManifest文件中

```
        <meta-data
            android:name="com.bumptech.glide.integration.okhttp3.OkHttpGlideModule"
            android:value="GlideModule"/>
```
这里一定保证是全路径
###Picasso
Picasso通过`Picasso.Builder`来配置，毕竟Builder风格是Square公司一贯的风格嘛
```
public class PicassoConfigFactory {
    private static Picasso sPicasso;

    public static Picasso getPicasso(Context context) {
        if (sPicasso == null) {
            sPicasso = new Picasso.Builder(context)
                    //硬盘缓存池大小
                    .downloader(new OkHttpDownloader(context, ConfigConstants.MAX_CACHE_DISK_SIZE))
                    //内存缓存池大小
                    .memoryCache(new LruCache(ConfigConstants.MAX_CACHE_MEMORY_SIZE))
                    .build();
        }
        return sPicasso;
    }
}
```

###ImageLoader
ImageLoader和Picasso很像
```
public class ImageLoaderFactory {
    private static ImageLoader sImageLoader;
    public static ImageLoader getImageLoader(Context context) {

        if (sImageLoader == null) {
            ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(context)
                    .diskCacheSize(ConfigConstants.MAX_CACHE_DISK_SIZE)
                    .memoryCacheSize(ConfigConstants.MAX_CACHE_MEMORY_SIZE)
                    .build();

            sImageLoader = ImageLoader.getInstance();
            sImageLoader.init(imageLoaderConfiguration);
        }
        return sImageLoader;
    }
}
```

###Fresco
Fresco使用`ImagePipelineConfig`来实现
```
    public static ImagePipelineConfig getImagePipelineConfig(Context context) {
        if (sImagePipelineConfig == null) {
            sImagePipelineConfig = ImagePipelineConfig.newBuilder(context)
                    .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(context)
                            .setMaxCacheSize(ConfigConstants.MAX_CACHE_DISK_SIZE)
                            .build())
                    .setBitmapMemoryCacheParamsSupplier(
                            new Supplier<MemoryCacheParams>() {
                                @Override
                                public MemoryCacheParams get() {
                                    return new MemoryCacheParams(ConfigConstants.MAX_CACHE_MEMORY_SIZE,
                                            Integer.MAX_VALUE,
                                            Integer.MAX_VALUE,
                                            Integer.MAX_VALUE,
                                            Integer.MAX_VALUE);
                                }
                            }
                    )
                    .build();
        }
        return sImagePipelineConfig;
    }
}
```
然后在Fresco初始化的事后，填充进去

```
 Fresco.initialize(context, FrescoConfigFactory.getImagePipelineConfig(context));
```
##图片随机出现

```
    public static final String[] URLS = {
            BASE + "CqmBjo5" + EXT, BASE + "zkaAooq" + EXT, BASE + "0gqnEaY" + EXT,
            BASE + "9gbQ7YR" + EXT, BASE + "aFhEEby" + EXT, BASE + "0E2tgV7" + EXT,
            BASE + "P5JLfjk" + EXT, BASE + "nz67a4F" + EXT, BASE + "dFH34N5" + EXT,
            BASE + "FI49ftb" + EXT, BASE + "DvpvklR" + EXT, BASE + "DNKnbG8" + EXT,
            BASE + "yAdbrLp" + EXT, BASE + "55w5Km7" + EXT, BASE + "NIwNTMR" + EXT,
            BASE + "DAl0KB8" + EXT, BASE + "xZLIYFV" + EXT, BASE + "HvTyeh3" + EXT,
            BASE + "Ig9oHCM" + EXT, BASE + "7GUv9qa" + EXT, BASE + "i5vXmXp" + EXT,
            BASE + "glyvuXg" + EXT, BASE + "u6JF6JZ" + EXT, BASE + "ExwR7ap" + EXT,
            BASE + "Q54zMKT" + EXT, BASE + "9t6hLbm" + EXT, BASE + "F8n3Ic6" + EXT,
            BASE + "P5ZRSvT" + EXT, BASE + "jbemFzr" + EXT, BASE + "8B7haIK" + EXT,
            BASE + "aSeTYQr" + EXT, BASE + "OKvWoTh" + EXT, BASE + "zD3gT4Z" + EXT,
            BASE + "z77CaIt" + EXT,
    };
    public void setRandomDatas() {
        Collections.addAll(mDatas, Data.URLS);
        Collections.shuffle(mDatas);
        List<String> copyDatas = new ArrayList<>(mDatas);
        mDatas.addAll(copyDatas);
        mDatas.addAll(copyDatas);
    }
```
##最终结果
先用网络加载图片，保证图片已经全部加载到本地。
|名称| 统计次数| 平均时间(ms)  |
| :------------: |:-------------:| :-----:|
| ImageLoader |  34|    57ms |
| Glide |  34|    77ms |
| Fresco| 34 | 91ms |
|  Picasso |  34|   278ms |

Glide：
![这里写图片描述](http://img.blog.csdn.net/20160706172532731)
Picasso：
![这里写图片描述](http://img.blog.csdn.net/20160706172607278)

 发现Picasso加载明显慢一个层次，特别是在快速滑动的时候，考虑到Picasso使用的是`Bitmap.Config.ARGB_8888`可能会消耗更多内存和GPU资源，我们把它修改为`Bitmap.Config.ARGB_4444`再试一遍
 ![这里写图片描述](http://img.blog.csdn.net/20160706175433693)
 
占用的内存的确从60+M减低到37M，考虑到图片本身体积也不大，这个量的确很大。
但是：平均加载时间还是279ms。
猜测这可能和Picasso的缓存策略选择缓存源图片大小，而其他框架默认选择缓存适应ImageView后的尺寸的原因导致

代码下载地址：https://github.com/zhouruikevin/ImageLoadPK