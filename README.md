# [ReadMe in English](https://github.com/Mararsh/MyBox/tree/master/en)

# MyBox：简易工具集
这是利用JavaFx开发的图形化界面程序，目标是提供简单易用的功能。免费开源。  

## 下载与运行
每个版本编译好的包已发布在[Releases](https://github.com/Mararsh/MyBox/releases?)目录下（点击上面的`releases`页签）。       

### 自包含程序包   
自包含的程序包无需java环境、无需安装、解包可用。（解包的目录名不要包含汉字）  
  
| 平台 | 链接 | 大小 | 启动文件 |    
| --- | --- | --- |  --- |   
| win | [MyBox-5.95-win.zip](https://github.com/Mararsh/MyBox/releases/download/v5.95/MyBox-5.95-win-exe.zip)  | 241MB | MyBox.exe |       
| linux | [MyBox-5.95-linux.tar.gz](https://github.com/Mararsh/MyBox/releases/download/v5.95/MyBox-5.95-linux.tar.gz)  | 200MB  | bin/MyBox  |     
| mac | [MyBox-5.95-mac.dmg](https://github.com/Mararsh/MyBox/releases/download/v5.95/MyBox-5.95-mac.dmg)  | 202MB  |  MyBox-5.95.app   |   

双击或者用命令行执行包内的启动文件即可运行程序。可以把图片/文本/PDF文件的打开方式关联到MyBox，这样双击文件名就直接是用MyBox打开了。
（目前无法双击打开路径包含汉字的文件） 
    
### Jar包   
在已安装JRE或者JDK 13或更高版本（`Oracle java`或`Open jdk`均可）的环境下，可以下载jar包。   
 
| 平台 | 链接 | 大小 | 运行需要 |    
| --- | --- | --- |  --- |   
| win | [MyBox-5.95-win-jar.zip](https://github.com/Mararsh/MyBox/releases/download/v5.95/MyBox-5.95-win-jar.zip)  | 128MB | Java 13.0.1或更高版本 |    
| linux | [MyBox-5.95-linux-jar.zip](https://github.com/Mararsh/MyBox/releases/download/v5.95/MyBox-5.95-linux-jar.zip)  | 134MB  | Java 13.0.1或更高版本 |    
| mac | [MyBox-5.95-mac-jar.zip](https://github.com/Mararsh/MyBox/releases/download/v5.95/MyBox-5.95-mac-jar.zip)  |  131MB  | Java 13.0.1或更高版本 |    
    
执行以下命令来启动程序：
<PRE><CODE>     java   -jar   MyBox-5.95.jar</CODE></PRE>
程序可以跟一个文件名作为参数、以用MyBox直接打开此文件。例如以下命令是打开此图片：
<PRE><CODE>     java   -jar   MyBox-5.95.jar   /tmp/a1.jpg</CODE></PRE>

### 其它下载地址  
从云盘下载：  https://pan.baidu.com/s/1fWMRzym_jh075OCX0D8y8A#list/path=%2F     
从sourceforge下载：https://sourceforge.net/projects/mara-mybox/files/     

### 限制   
自包含包：无法在包含非英文字符的路径下启动；而且无法双击打开包含汉字的文件。已向jpackage开发组报告这个问题：
[JDK-8232936](http://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8232936) 

本版本仍未能支持在Linux/Mac上文字识别，原因是：所依托的tesseract库与各种平台紧密相关，没有统一的打包途径。   
时间有限，目前暂搁此需求。


# 资源地址
项目主页：https://github.com/Mararsh/MyBox

源代码和编译好的包：https://github.com/Mararsh/MyBox/releases

在线提交软件需求和问题报告：https://github.com/Mararsh/MyBox/issues

云盘地址：https://pan.baidu.com/s/1fWMRzym_jh075OCX0D8y8A#list/path=%2F

在线帮助：https://mararsh.github.io/MyBox/mybox_help_zh.html

sourceforge:  https://sourceforge.net/projects/mara-mybox/files/


# 文档    

| 文档名 | 版本 | 修改时间  | 链接 |
| --- | --- | --- | --- |
| 用户手册-综述 |  5.0 |  2019-4-19 | [PDF](https://github.com/Mararsh/MyBox/releases/download/v5.0/MyBox-UserGuide-5.0-Overview-zh.pdf) |
| 用户手册-图像工具 | 5.0 |  2019-4-18 | [PDF](https://github.com/Mararsh/MyBox/releases/download/v5.0/MyBox-UserGuide-5.0-ImageTools-zh.pdf) |
| 用户手册-PDF工具 | 5.0 |  2019-4-20 | [PDF](https://github.com/Mararsh/MyBox/releases/download/v5.0/MyBox-UserGuide-5.0-PdfTools-zh.pdf) |
| 用户手册-桌面工具 | 5.0 |  2019-4-16 | [PDF](https://github.com/Mararsh/MyBox/releases/download/v5.0/MyBox-UserGuide-5.0-DesktopTools-zh.pdf) |
| 用户手册-网络工具 | 5.0 |  2019-4-16 | [PDF](https://github.com/Mararsh/MyBox/releases/download/v5.0/MyBox-UserGuide-5.0-NetworkTools-zh.pdf) |
| 开发指南 | 2.0 |  2019-11-18 | [PDF](https://github.com/Mararsh/MyBox/releases/download/v5.8/MyBox-DevGuide-2.0-zh.pdf) |
| 快捷键 | 5.9 |  2019-12-21 | [html](https://mararsh.github.io/MyBox/mybox_shortcuts.html) |
| 开发日志 | 5.95 |  2019-12-26 | [html](#devLog) |


# 实现基础
MyBox基于以下开源软件：  

| 软件 | 角色 | 链接 |  
| --- | --- | --- | 
| JDK | Java语言 | http://jdk.java.net/13/    
|   |   | https://www.oracle.com/technetwork/java/javase/downloads/index.html  |   
| JavaFx | 图形化界面 | https://gluonhq.com/products/javafx/  |     
|   |   |  https://docs.oracle.com/javafx/2/  |     
|   |   |  https://www.oracle.com/technetwork/java/javafxscenebuilder-1x-archive-2199384.html |     
| NetBeans | 集成开发环境 | https://netbeans.org/ |     
| jpackage | 自包含包 | http://jdk.java.net/jpackage/ |     
| jai-imageio | 图像处理 | https://github.com/jai-imageio/jai-imageio-core |   
| PDFBox | PDF处理 | https://pdfbox.apache.org/ |   
| PDF2DOM | PDF转换html | http://cssbox.sourceforge.net/pdf2dom/ |   
| javazoom | MP3解码 | http://www.javazoom.net/index.shtml |      
| log4j | 日志处理 | https://logging.apache.org/log4j/2.x/ |      
| Derby | 数据库 | http://db.apache.org/derby/ |   
| GifDecoder | 解码不规范的Gif | https://github.com/DhyanB/Open-Imaging/ |   
| EncodingDetect | 检测文本编码 | https://www.cnblogs.com/ChurchYim/p/8427373.html |   
| Free Icons | 图标 | https://icons8.com/icons/set/home |  
| Lindbloom | 色彩理论 | http://brucelindbloom.com/index.html |  
| tess4j | OCR | https://github.com/nguyenq/tess4j |  
| tesseract | OCR | https://github.com/tesseract-ocr/tesseract |   
| barcode4j | 生成条码 | http://barcode4j.sourceforge.net |  
| zxing | 生成/解码条码 | https://github.com/zxing/zxing |   
| flexmark-java | 转换Markdown | https://github.com/vsch/flexmark-java |   
| commons-compress | 归档/压缩 | https://commons.apache.org/proper/commons-compress |   
| XZ for Java | 归档/压缩 | https://tukaani.org/xz/java.html |   
| jaffree | 封装ffmpeg | https://github.com/kokorin/Jaffree |   
| ffmpeg| 媒体转换/生成 | http://ffmpeg.org |   
| image4j | 读ico格式 | https://github.com/imcdonagh/image4j |   
| AutoCommitCell | 提交单元的修改 | https://stackoverflow.com/questions/24694616 （Ogmios） |   


# 当前版本
当前是版本5.95，已实现的特点概述如下:
* [跨平台](#cross-platform)
* [国际化](#international)
* [PDF工具](#pdfTools)
* [图像工具](#imageTools)
    - [查看图像](#viewImage)
    - [浏览图像](#browserImage)  
    - [分析图像](#ImageData)  
    - [图像处理](#imageManufacture)   
    - [调色盘](#ColorPalette)
    - [图片转换](#imageConvert)
    - [识别图像中的文字](#imageOCR)
    - [多帧图像文件](#multiFrames)
    - [多图合一](#multipleImages)
    - [图像局部化](#imagePart)
    - [大图片的处理](#bigImage)
    - [其它](#imageOthers)
* [数据工具](#dataTools)
    - [矩阵计算](#matrixTool)
    - [色彩空间](#colorSpaces)
    - [生成条码](#createBarcodes)
    - [解码条码](#decodeBarcodes)
    - [消息摘要](#messageDigest)
* [文件工具](#fileTools)
    - [编辑文本](#editText)
    - [编辑字节](#editBytes)
    - [管理文件/目录](#directoriesArrange)
    - [归档/压缩/解压/解档](#archiveCompress)
    - [检查冗余文件](#filesRedundancy)
    - [其它](#fileOthers)  
* [媒体工具](#MediaTools)
    - [播放视频/音频](#mediaPlayer)
    - [管理播放列表](#mediaList)
    - [封装ffmpeg的功能](#ffmpeg)
    - [其它](#mediaOthers)
* [网络工具](#netTools)
    - [编辑网页](#htmlEditor)
    - [浏览器](#webBrowser)
    - [管理安全证书](#securityCerificates)
    - [编辑Markdown](#markdownEditor)
    - [html与Markdown互换](#htmlMarkdownConversion)
    - [微博截图工具](#weiboSnap)
* [设置](#settings)
* [窗口](#windows)
* [帮助](#helps)
* [配置](#Config)   
* [对于高清晰屏幕的支持](#Hidpig)    
    
## 跨平台<a id="cross-platform" />   
MyBox用纯Java实现且只基于开源库，因此MyBox可运行于所有支持Java 13的平台。（MyBox v5.3以前的版本均基于Java 8）

图像识别文字目前只支持windows平台。

## 国际化<a id="international" />
所有代码均国际化。可实时切换语言。目前支持中文、英文。扩展语言只需编辑资源文件。

## PDF工具<a id="pdfTools" />
1. 以网页模式查看PDF文件，可逐页查看和编辑页面和html。标签和缩略图。
2. 批量将PDF转换为网页，可选：每页保存为一个html、还是整个PDF保存为一个html；字体文件/图像文件是嵌入、单独保存、还是忽略。
3. 以图像模式查看PDF文件，可设置dpi以调整清晰度，可以把页面剪切保存为图片。
4. 在图像模式下，识别PDF页面中的文字（OCR）。
   批量识别时，可设置：
	-  页面转换图片还是页面提取图片
	-  像素密度或伸缩比例
	-  九种图像增强算法
	-  黑白阈值
	-  旋转角度。
	-  是否自动矫正偏斜
	-  是否反色
	-  语言列表及其顺序
	-  是否插入分页行
5. 将PDF文件的每页转换为一张图片，包含图像密度、色彩、格式、压缩、质量、色彩转换等选项。
6. 将多个图片合成PDF文件，可以设置压缩选项、页面尺寸、页边、页眉、作者等。
   支持中文，程序自动定位系统中的字体文件，用户也可以输入ttf字体文件路径。
7. 压缩PDF文件的图片，设置JPEG质量或者黑白色阈值。
8. 合并多个PDF文件。
9. 分割PDF文件为多个PDF文件，可按页数或者文件数来均分，也可以设置起止列表。
10. 将PDF中的图片提取出来。可以指定页码范围。
11. 将PDF文件中的文字提取出来，可以定制页的分割行。
12. 修改PDF的属性，如：标题、作者、版本、修改时间、用户密码、所有者密码、用户权限等
13. PDF的批量处理。
14. 可设置PDF处理的主内存使用量。

## 图像工具<a id="imageTools" />

### 查看图像<a id="viewImage" />
1. 设置加载宽度：原始尺寸或指定宽度。
2. “选择模式”：处于选择模式时，剪裁、复制、另存，都是针对选择的区域，否则是针对整个图像。
3. 旋转可保存。
4. 删除、重命名、恢复。
5. 可选显示：坐标、横标尺、纵标尺、数据。
6. 查看图像的元数据和属性，可解码图像中嵌入的ICC特性文件。
7. 同目录下图像文件导览，多种文件排序方式。
### 浏览图像<a id="browserImage" />
1. 同屏显示多图，分别或者同步旋转和缩放。
2. 旋转可选保存。
3. 格栅模式：可选文件数、列数、加载宽度
4. 文件列表模式
5. 缩略图列表模式
6. 重命名、删除
### 图像处理<a id="imageManufacture" />
1. 粘贴板。
	-  数据来源：对图像整体或选择的部分做“复制”（CTRL+c）、剪切下来的图片部分、系统粘贴板、系统中的图片文件、示例图片。
	-  管理粘贴板列表：增、删、清除、刷新，可设置最多保存数。
	-  编辑图像时随时可以按粘贴按钮（CTRL+v）以把粘贴板的第一张图贴到当前图片上，也可以双击粘贴板列表的项目以粘贴。
	-  在当前图片上拖拉被粘贴图片，调整大小和位置。
	-  粘贴选项：是否保持宽高比、混合模式、不透明度、旋转角度。
2. 剪裁：定义“范围”以设置要剪切的内容。可设置背景色，可选是否把剪切下来的部分放入粘贴板。
3. 伸缩：拖动锚点调整大小、按比例收缩、或设置像素。四种保持宽高比的选项。
4. 色彩：针对红/蓝/绿/黄/青/紫通道、饱和度、明暗、色相、不透明度，进行增加、减少、设值、过滤、取反色的操作。可选是否预乘透明。
5. 效果：海报（减色）、阈值化、灰色、黑白色、褐色、浮雕、边沿检测、马赛克、磨砂玻璃。可选算法和参数。
6. 增强：对比度、平滑、锐化、卷积。可选算法和参数。
7. 富文本：以网页形式编辑文本，在图片上拖放调整文本的大小和位置。可设置背景的颜色、不透明度、边沿宽度、圆角大小，可设置文字的旋转角度。
   由于是利用截屏实现，结果比较模糊，还没有好的解决办法。
8. 文字：设置字体、风格、大小、色彩、不透明度、阴影、角度，可选是否轮廓、是否垂直，点击图片定位文字。
9. 画笔：
	-  折线：多笔一线。可选画笔的宽度、颜色、是否虚线、不透明度。
	-  线条：一笔一线。可选画笔的宽度、颜色、是否虚线、不透明度。
	-  橡皮檫：一笔一线。总是透明色，可选画笔的宽度。
	-  磨砂玻璃：一点一画。可选画笔的宽度、模糊强度、形状（圆形还是方形）。
	-  马赛克：一点一画。可选画笔的宽度、模糊强度、形状（圆形还是方形）。
	-  形状：矩形、圆形、椭圆、多边形。可选画笔的宽度、颜色、是否虚线、不透明度、是否填充、填充色。
10. 变形：斜拉、镜像、旋转，可设置参数。
11. 圆角：把图像四角改为圆角，可设置背景色、圆角大小。
12. 阴影：可设置背景色、阴影大小、是否预乘透明。
13. 边沿：模糊边沿，可设置是否预乘透明；拖动锚点以调整边沿；按宽度加边；按宽度切边；按颜色切边。可选四边、颜色。
14. 图片历史：
	- 对于图片的每一次修改，工具可以自动保存为图片历史。可选是否把“加载”也记录为历史。
	- 管理历史：删除、清除、选择并恢复为当前图片，可设置最多保存的历史个数。
	- 对上一步的撤销（CTRL+z）和重做（CTRL+y）。可以随时恢复原图（CTRL+r）。也可以选择历史列表中任意图片来恢复。
15. 参照图：可以打开其它图片以作对比。
16. “范围”：定义操作针对的像素内容，既可定义区域、定义颜色匹配规则，也可同时定义区域和颜色匹配。
	- 定义区域：可以是矩形、圆形、椭圆、多边形，区域可反选。
	- 定义要匹配颜色列表，可以利用调色盘在图片上直接取色。
	- 选择颜色匹配的对象，可以是红/蓝/绿通道、饱和度、明暗、色相，色距可定义。颜色匹配结果可反选。
	- 抠图：匹配像素周围的像素、并按同一匹配规则持续扩散出去。多个像素点的匹配合集就是结果。
	- 轮廓：把背景透明的图片的轮廓自动提取出来，作为操作的范围。
	- 范围可作用于：复制、剪切、颜色、效果、和卷积。
	- 图片历史和参照图也可以定义“范围”，以便把“范围”内的部分复制到粘贴板中  
	- 保存和管理范围：增、删、改、清除，应用已保存的范围。    
17. 弹出图片：当前图片、图片历史、参照图都可以显示在弹出的新窗口中，可选择弹出窗口是否总是在最上面。 
18. 可选是否同步缩放当前图片、图片历史、参照图。
19. 修改已有的图片，或新建图片。
20. “按需可见”的界面布局：左右幕布式区域、上下风箱式菜单、多页签切换目标、子功能区更细化的显示/隐藏/调整。
21. 批量图像处理。
22. 演示：对于“颜色”、“效果”、“增强”，一键展示各种数据处理的示例。
### 调色盘<a id="ColorPalette" />
1. 可保存任意色彩。可自动填写139种常用色彩。
2. 色块直观显示颜色。弹出颜色的名字（如果有）、十六进制值、rgb值、hsb值、不透明值、cmyk值、cie值。
3. 可以给每个颜色命名。
4. 可选把调色盘中颜色导出为html列表。
5. 可在当前图片、图片历史、或参照图上点击取色。
### 分析图像<a id="ImageData" />
1. 统计显示图像的数据：各颜色成分的均值/方差/斜率/中值/众数/最大/最小，以及直方图。
2. 直方图的颜色成分可多选。
3. 可针对选择的矩形区域做统计显示。
4. 利用K-Means聚类计算最不同的颜色。
5. 利用统计量化计算出现最多的颜色。
6. 图像数据可以被保存为html文件。
### 图片转换<a id="imageConvert" />
1. 可选图像文件的格式，包括：png,jpg,bmp,tif,gif,wbmp,pnm,pcx，raw。
2. 可选颜色空间，包括：sRGB、Linear sRGB、ECI RGB、Adobe RGB、Apple RGB、Color Match RGB、ECI CMYK、Adobe CMYK(多种)、灰色、黑白色。
3. 可选外部ICC特性文件作为转换的依据。
4. 对于jpg/png格式可选是否嵌入ICC特性文件，对于Tif格式必选嵌入。
5. 可选对透明通道（如果有）的处理：保留、删除、预乘并保留、预乘并删除。
6. 可选压缩类型和质量。
7. 对于黑白色，可选二值化算法：OTSU、缺省、或输入预置，可选是否抖动处理。
8. 批量转换。
### 识别图像中的文字<a id="imageOCR" />
1. 对图像预处理：
	-  九种图像增强算法
	-  伸缩比例
	-  黑白阈值
	-  旋转角度。
	-  是否自动矫正偏斜
	-  是否反色
2. 文字识别的选项：
	-  数据文件列表及其顺序
	-  是否生成“区域”数据，及其粒度
	-  是否生成“词”数据，及其粒度
3. 单图识别：
	- 可以保存并加载预处理后的图像。
	- 可以设置需要识别的矩形区域。
	- 同步显示：预处理后的图像、原图、和识别出的文字及其html。
	- 以html显示“区域”数据和“词”数据，并可保存为文件。
	- 演示：一键展示各个图像增强算法的示例。
4. 批量识别：
	-  可选是否同时生成html或PDF
	-  可选是否合并识别出文字
5. 内置英文和中文的“最快的”数据文件，在windows上解包即用。用户也可以按需下载更多数据文件。
### 多帧图像文件<a id="multiFrames" />
1. 查看、提取多帧图像文件
2. 创建、编辑多帧tiff文件
3. 查看/提取/创建/编辑动画Gif文件。可设置间隔、是否循环、图片尺寸
### 多图合一<a id="multipleImages" />
1. 图片的合并。支持排列选项、背景颜色、间隔、边沿、和尺寸选项。
2. 将多个图片合成PDF文件
3. 添加透明通道
### 图像局部化<a id="imagePart" />
1. 图像的分割。支持按个数分割、按尺寸分割、和定制分割。可以保存为多个图像文件、多帧Tiff文件、或者PDF。
2. 图像的降采样。可以设置采样区域、采样比例。
3. 提取透明通道
### 大图片的处理<a id="bigImage" />
1. 评估加载整个图像所需内存,判断能否加载整个图像。
2. 若可用内存足够载入整个图像，则读取图像所有数据做下一步处理。尽可能内存操作而避免文件读写。
3. 若内存可能溢出，则采样读取图像数据做下一步处理。
4. 采样比的选择：即要保证采样图像足够清晰、又要避免采样数据占用过多内存。
5. 采样图像主要用于显示图像。已被采样的大图像，不适用于图像整体的操作和图像合并操作。
6. 一些操作，如分割图像、降采样图像，可以局部读取图像数据、边读边写，因此适用于大图像：显示的是采样图像、而处理的是原图像。
### 其它<a id="imageOthers" />
1. 支持图像格式：png,jpg,bmp,tif,gif,wbmp,pnm,pcx。可读Adobe YCCK/CMYK的jpg图像。
2. 像素计算器
3. 卷积核管理器

## 数据工具<a id="dataTools" />

### 矩阵计算<a id="matrixTool" />
1. 矩阵数据的编辑：
	-  对于输入或粘贴的数据，过滤特殊字符，以适应带格式的数据。
	-  自动把当前矩阵数据转变为行向量、列向量、或指定列数的矩阵。
	-  自动生成单位矩阵、随机方阵、或随机矩阵，可设置行/列数。   
2. 矩阵的一元计算：转置、行阶梯形、简化行阶梯形、行列式值-用消元法求解、行列式值-用余子式求解
	、逆矩阵-用消元法求解、逆矩阵-用伴随矩阵求解、矩阵的秩、伴随矩阵、余子式、归一化、
	、设置小数位数、设为整型、乘以数值、除以数值、幂。
3. 矩阵的二元计算：加、减、乘、克罗内克积、哈达马积、水平合并、垂直合并。
	
### 色彩空间<a id="colorSpaces" />
1. 绘制色度图
	-  标准数据的轮廓线：CIE 1931 2度观察者（D50）、CIE 1964 10度观察者（D50）、CIE RGB色域、ECI RGB色域、sRGB色域、
	   Adobe RGB色域、Apple RGB色域、PAL RGB色域、NTSC RGB色域、ColorMath ProPhoto RGB色域、SMPTE-C RGB色域。
	-  标准光源（白点）：A、C、D50、D55、D65、E。
	-  用户可填写刺激值或色坐标、或选择色彩，工具自动计算各种色彩空间对应的色彩数值、并把计算值显示在色度图上。
	-  用户可输入或用文件导入光谱数据，工具自动过滤掉特殊字符、并把光谱数据显示在色度图上。
	-  用户可以选择在色度图上显示/不显示以上数据。
	-  用户可选色度图的背景为透明/白色/黑色，可选轮廓线的点尺寸或线尺寸，可选是否显示格栅和波值。
	-  工具以表格和文本显示标准数据：CIE 1931 2度观察者1nm、CIE 1931 2度观察者5nm、CIE 1964 10度观察者1nm、CIE 1964 10度观察者5nm，
	   用户可导出数据的文本。
2. 编辑ICC色彩特性文件
	-  预置标准ICC文件：Java内嵌的ICC文件（包括sRGB、XYZ、PYCC、GRAY、LINEAR_RGB）、
	   ECI提供的ICC文件（包括ECI_CMYK、ECI_RGB_v2）和Adobe提供的ICC文件（包括Adobe RGB、Apple RGB、及多种CMYK ICC文件）。
	-  头部所有字段可编辑。在保存ICC文件时，工具自动计算“profile id”字段（MD5摘要）。
	-  标签表：标签、名字、类型、偏移、大小、描述、解码后的数据、数据的原始值（十六进制字节）
	-  可编辑的标签类型：Text、MultiLocalizedUnicode、Signature、DateTime、XYZ、Curve、ViewingConditions、Measurement、S15Fixed16Array。
	   当前版本不支持编辑LUT类型的标签。
	-  选项：把LUT表中的数据归一化到0~1。
	-  整个ICC数据被解析显示为XML，并可导出。未被解码的数据显示为十六进制字节。
	-  读入的ICC数据可以修改另存为新的ICC文件。
3. RGB色彩空间
	-  用户选择或输入RGB色彩空间（基色和白点）、选择或输入要适应的参考白点，工具自动计算色适应后的基色值，并展示计算过程。
	-  可设置小数位数。
	-  色适应算法可选：Bradford、XYZ Scaling、Von Kries。
	-  预置的标准RGB色彩空间包括：CIE RGB、ECI RGB、sRGB、Adobe RGB、Apple RGB、PAL RGB、NTSC RGB、ColorMath ProPhoto RGB、SMPTE-C RGB
    -  预置的标准光源包括CIE 1931和CIE 1964的：A、B、C、D50、D55、D65、D75、E、F1~F12。 
	-  工具以表格和文本显示：不同的标准RGB色彩空间、不同的标准光源、不同的算法所计算出的色适应后的基色值。用户可导出数据的文本。
4. 线性RGB到XYZ的转换矩阵
	-  用户选择或输入RGB色彩空间（基色和白点）、选择或输入XYZ空间的参考白点，工具自动计算线性RGB到XYZ的转换矩阵，并展示计算过程。
	-  以表格和文本显示：不同的标准RGB色彩空间、不同的XYZ空间参考白点、不同的算法所计算出的转换矩阵。用户可导出数据的文本。
5. 线性RGB到线性RGB的转换矩阵
	-  用户选择或输入源和目标的RGB色彩空间（基色和白点），工具自动计算源线性RGB到目标线性RGB的转换矩阵，并展示计算过程。
	-  工具以表格和文本显示：不同的标准RGB色彩空间之间以不同的算法所计算出的转换矩阵。用户可导出数据的文本。
6. 光源
	-  用户输入源颜色（相对值/色度坐标/刺激值）、选择或输入源白点和目标白点，工具自动计算色适应后的颜色值，并展示计算过程。
	-  工具以表格和文本显示标准光源的数据值、色温和说明。用户可导出数据的文本。
7. 色度适应矩阵
	-  用户选择或输入源白点和目标白点，工具自动计算色度适应矩阵，并展示计算过程。
	-  工具以表格和文本显示不同标准光源之间不同的算法的色度适应矩阵。用户可导出数据的文本。   

### 生成条码<a id="createBarcodes" />
1. 支持的一维码类型： Code39, Code128, Codabar, Interleaved2Of5, ITF_14, POSTNET, EAN13, EAN8, EAN_128, UPCA, UPCE,
        Royal_Mail_Customer_Barcode, USPS_Intelligent_Mail
2. 支持的二维码类型：QR_Code, PDF_417, DataMatrix
3. 一维码选项：朝向、宽高、分辨率、文字位置、字体大小、空白区宽度等。不同类型的选项不同。
4. 二维码选项：宽高、边沿、纠错级别、压缩模式。不同类型的选项不同。
5. 二维码QR_Code可以在中心显示一个图片。根据纠错级别自动调整图片大小。
6. 示例参数和建议值。
7. 对生成的条码即时检验。

### 解码条码<a id="decodeBarcodes" />
1. 支持的一维码类型： Code39, Code128, Interleaved2Of5, ITF_14,  EAN13, EAN8, EAN_128, UPCA, UPCE
2. 支持的二维码类型：QR_Code, PDF_417, DataMatrix
3. 显示条码内容和元数据（条码类型、纠错级别等） 

### 消息摘要<a id="messageDigest" />
1. 生成文件或者输入文本的消息摘要   
2. 支持MD2, MD5, SHA-1, SHA-224, SHA-256, SHA-384, SHA-512/224, SHA-512/256, SHA3-224, SHA3-256, SHA3-384, SHA3-512   

## 文件工具<a id="fileTools" />
### 编辑文本<a id="editText" />
1. 自动检测或手动设置文件编码；改变字符集实现转码；支持BOM设置。
2. 自动检测换行符；改变换行符。显示行号。
3. 支持LF（Unix/Linux）、 CR（Apple）、 CRLF（Windows）。
4. 查找与替换。可只本页查找/替换、或整个文件查找/替换。计数功能。支持正则表达式。
5. 定位。跳转到指定的字符位置或行号。
6. 行过滤。条件：“包含任一”、“不含所有”、“包含所有”、“不含任一”。支持正则表达式。
7. 可累加过滤。可保存过滤结果。可选是否包含行号。
8. 字符集对应的编码：字节的十六进制同步显示、同步滚动、同步选择。
9. 分页。可用于查看和编辑非常大的文件，如几十G的运行日志。
	-  可以设置页尺寸。
	-  页面导航。
	-  先加载显示首页，同时后端扫描文件以统计字符数和行数；统计期间部分功能不可用；统计完毕自动刷新界面。
	-  对于跨页字符串，确保查找、替换、过滤的正确性。
10. 通用的编辑功能（复制/粘贴/剪切/删除/全选/撤销/重做/恢复）及其快捷键。

### 编辑字节<a id="editBytes" />
1. 字节被表示为两个十六进制字符。所有空格、换行、非法值将被忽略。
2. 常用ASCII字符的输入选择框。
3. 换行。仅用于显示、无实际影响。显示行号。可按字节数换行、或按一组字节值来换行。
4. 查找与替换。可只本页查找/替换、或整个文件查找/替换。计数功能。支持正则表达式。
5. 定位。跳转到指定的字节位置或行号。
6. 行过滤。条件：“包含任一”、“不含所有”、“包含所有”、“不含任一”。可累加过滤。可保存过滤结果。可选是否包含行号。支持正则表达式。
7. 选择字符集来解码：同步显示、同步滚动、同步选择。非字符显示为问号。
8. 分页。可用于查看和编辑非常大的文件，如几十G的二进制文件。
	- 可以设置页尺寸。
	- 页面导航。
	- 先加载显示首页，同时后端扫描文件以统计字节数和行数；统计期间部分功能不可用；统计完毕自动刷新界面。
	- 对于跨页字节组，确保查找、替换、过滤的正确性。若按字节数换行，则行过滤时不考虑跨页。
9. 通用的编辑功能（复制/粘贴/剪切/删除/全选/撤销/重做/恢复）及其快捷键。

### 管理文件/目录<a id="directoriesArrange" />
1. 查找、删除、复制、移动、重命名。
2. 目录同步，包含复制子目录、新文件、特定时间以后已修改文件、原文件属性，以及删除源目录不存在文件和目录，等选项。
3. 整理文件，将文件按修改时间或者生成时间重新归类在新目录下。此功能可用于处理照片、游戏截图、和系统日志等需要按时间归档的批量文件。

### 归档/压缩/解压/解档<a id="archiveCompress" />  
1. 归档是把多个文件/目录聚集为单个文件的过程，有的归档格式支持同时实现压缩（如zip和7z）。解档是还原归档文件的过程。 
2. 压缩是把单个文件转变为一个更小的文件的过程。通常是先归档再压缩。解压是还原压缩文件的过程。  
3. 支持归档格式： zip, tar, 7z, ar, cpio。  
4. 支持解档格式： zip, tar, 7z, ar, cpio, arj, dump。  
5. 支持压缩格式：gzip, bzip2, xz, lzma, Pack200, DEFLATE, snappy-framed, lz4-block, lz4-framed。 
6. 支持解压格式：gzip, bzip2, xz, lzma, Pack200, DEFLATE, snappy-framed, lz4-block, lz4-framed, DEFLATE64,  Z。 
7. 解档/解压时，可自动检测格式，也可由用户指定格式（有的格式无法自动检测出来）。 
8. 解档时，提供树状结构方便用户选择提取内容。 

### 检查冗余文件<a id="filesRedundancy" />  
1. 根据MD5检查重复的文件。  
2. 提供树状结构方便用户删除冗余文件。   
3. 支持边检查边删除。  

### 其它<a id="fileOthers" />
1. 批量转换文件的字符集。
2. 批量转换文件的换行符。
3. 切割文件。切割方式可以是：按文件数、按字节数、或按起止列表。
4. 合并文件。
5. 比较文件（字节）。
6. 批量处理时，选择文件的方式：扩展名、文件名、文件大小、文件修改时间，支持正则表达式。

## 媒体工具<a id="MediaTools" />

### 播放视频/音频<a id="mediaPlayer" />
1. 创建/加载播放列表
2. 选项：自动播放、显示毫秒、循环次数、随机顺序
3. 设置：音量、速度（0~8倍）
4. 按键：播放/暂停/停止/上一个/下一个/媒体信息/静音/全屏
5. 全屏时：触屏短暂显示控件、ESC退出全屏
6. 支持的容器格式：aiff, mp3, mp4, wav, hls(m3u8)，支持的视频编码：h.264/avc，支持的音频编码：aac, mp3, pcm。   
    如果视频没有声音，这是因为播放器不支持它的音频编码器。    
    已知问题：播放一些流媒体时MyBox可能崩溃退出。   
7. 乖乖和笨笨的声音
8. 此工具无需ffmpeg。但是在Linux上需要安装libavcodec和libavformat， 参见：      
https://www.oracle.com/technetwork/java/javafx/downloads/supportedconfigurations-1506746.html

### 管理播放列表<a id="mediaList" />
1. 增删改播放列表
2. 增删改播放列表的内容。
3. 读取所支持媒体格式的信息：时长、音频编码、视频编码

### 封装ffmpeg的功能<a id="ffmpeg" />
1. 批量转换音频/视频-文件/目录表
2. 批量转换音频/视频-流和媒体信息表
3. 把图片和音频合成为视频-文件/目录
4. 把图片和音频合成为视频-图片信息
5. 转换/合成媒体时，可选择/设置所有的参数，包括格式、视频编码、音频编码、字幕、视频帧率、宽高比、音频采样率、改变音量等。
6. 合成图片视频时，可以单独设置每个图片的时长，也可对全部图片设置时长，可选择是否”音频流结束时结束视频“。
7. 合成图片视频时，图片被自动适应为屏幕大小且保持宽高比。
8. 利用ffprobe读取媒体的信息：格式、音频流、视频流、帧、包、支持的像素格式。
9. 读取ffmpeg的信息：版本、格式、支持的编码解码器、支持的滤镜，以及自定义查询参数。    
注：这一组功能依赖于ffmpeg，需要用户自己下载ffmpeg（建议使用静态版本）。

### 其它<a id="mediaOthers" />
1. 记录系统粘贴板中的图像：保存或查看粘贴板中的图像，可选无损图像或压缩类型。
2. 闹钟，包括时间选项和音乐选项，支持铃音“喵”、wav铃音、和MP3铃音，可以在后端运行。

## 网络工具<a id="netTools" />

### 编辑网页<a id="htmlEditor" />
1. 以富文本方式编辑本地网页或在线网页。（不支持FrameSet）
2. 直接编辑HTML代码。（支持FrameSet）
3. 网页浏览器显示编辑器内容、或在线网页。支持前后导览、缩放字体、截图页面为整图或者PDF文件。
4. 同步转换为Markdown。

### 浏览器<a id="webBrowser" />
1. 多页签显示网页
2. 管理浏览历史
3. 在线安装网站SSL证书。
4. 可选忽略指定网站或全部网站的SSL证书的验证（可用于证书有问题的网页，但是可能导致安全风险）。

### 管理安全证书<a id="securityCerificates" />
1. 读取任意密钥库文件中的证书内容，可导出为html文件
2. 添加/读取任意证书文件的内容
3. 下载并安装任意网址的证书。
4. 删除密钥库中的证书。
5. 修改密钥库时自动备份

### 编辑Markdown<a id="markdownEditor" />
1. 提供输入格式的按钮。
2. 同步转换为html，提供转换选项。
3. 同步转换为文本。
4. 查找与替换。支持正则表达式。
5. 定位。跳转到指定的字符位置或行号

### html与Markdown互换<a id="htmlMarkdownConversion" />
1. html到Markdown的批量转换。
2. Markdown到html的批量转换。

### 微博截图工具<a id="weiboSnap" />
1. 自动保存任意微博账户的任意月份的微博内容
2. 设置起止月份。
3. 确保页面完全加载，可以展开页面包含的评论、可以展开页面包含的所有图片。
4. 将页面保存为本地html文件。由于微博是动态加载内容，本地网页无法正常打开，仅供获取其中的文本内容。
5. 将页面截图保存为PDF。可以设置截图的格式、像素密度，和PDF的页尺寸、边距、作者等。
6. 将页面包含的所有图片的原图全部单独保存下来。
7. 实时显示处理进度。
8. 可以随时中断处理。程序自动保存上次中断的月份并填入作本次的开始月份。
9. 可以设置错误时重试次数。若超时错误则自动加倍最大延迟时间。    

## 设置<a id="settings" />
1. 是否恢复界面上次尺寸、是否在新窗口中打开界面、是否弹出最近访问的文件/目录
2. 语言、字体大小、皮肤、按钮颜色和大小
3. 画笔/锚点的宽度和颜色、锚点是否实心
4. 图像是否显示坐标、横标尺、纵标尺。
5. 图像历史个数、图像最大显示宽度
6. 不支持Alpha时要替换的颜色（建议为白色）
7. PDF可用最大主内存
8. 退出程序时是否关闭闹钟
9. 基础参数：最大可用内存、是否关闭分辨率感知、数据目录。修改这几个参数将会使MyBox自动重启。
10. Derby数据库的运行模式。提醒：在一些机器上启动和关闭Derby网络模式都非常慢。 
11. 清除个人设置、查看用户目录。

## 窗口<a id="windows" />
1. 开/关内存监视条
2. 开/关CPU监视条
3. 刷新/重置/全屏窗口
4. 关闭其它窗口
5. 最近访问的工具

## 帮助<a id="helps" />
1. MyBox快捷键列表
2. MyBox的属性
3. 用户手册（网址）
4. 开发指南（网址）

## 配置<a id="Config" />

### 缺省的配置文件“MyBox.ini”
缺省的配置文件“MyBox.ini”在“用户目录”下，如windows就是“C:\Users\<用户名>\mybox\MyBox.ini”。   
只有一种方式可以临时改变配置文件：在命令行启动jar包时设置参数“config=\"配置文件名\"”。

### 配置文件的内容
配置文件中包含以下参数    
1. 数据根目录，如：   
<PRE><CODE>     MyBoxDataRoot=/home/mara/mybox2/ </CODE></PRE>
2. JVM内存使用量，如：   
<PRE><CODE>     JVMmemory=-Xms3026m </CODE></PRE>
3. 是否“关闭分辨率感知”，如：  
<PRE><CODE>     DisableHidpi=false </CODE></PRE>    
4. Derby数据库的运行模式，如：  
<PRE><CODE>     DerbyMode=embedded </CODE></PRE>    

用户可以编辑配置文件，并手动将旧数据目录文件复制到新数据目录下。   
用户也可以在工具的“设置”界面上修改数据根目录，工具将自动复制旧数据。 

除了DerbyMode，在线修改这些参数将会使MyBox自动重启。


## 对于高清晰屏幕的支持<a id="Hidpi" />  

Java 9以后已支持HiDPI，控件和字体都会适应当前清晰度配置。MyBox支持在线关闭/打开DPI敏感，修改时MyBox会自动重启。   
开发者需要注意的是：JavaFx虚拟屏幕的dpi不同于物理屏幕的dpi，对于窗口元素尺寸的计算还要考虑伸缩比。   


# 开发日志<a id="devLog" />  
2019-12-26 版本5.95  改进批处理界面：使用多页签而不是把控件挤在一个页面上。      
解决问题：避免微博截图工具414错误；图像处理的界面控件显示逻辑混乱；图像批处理的保存格式不生效。        
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av5.95)      
今天缅怀毛主席和他的战友， 他们使中国人民站起来了。

2019-12-21 版本5.9 支持多页签的浏览器。 可管理浏览历史、在线安装网站SSL证书。   
读取任意密钥库文件中的证书内容，可导出为html文件。在密钥库中添加证书文件的内容、或下载安装网址的证书。   
视频/音频播放器，可设置自动播放、显示毫秒、循环次数、随机顺序、音量、速度、静音、全屏等。乖乖和笨笨的声音。管理播放列表。   
封装ffmpeg的功能：批量转换音频/视频、把图片和音频合成为视频、读取媒体的信息、读取ffmpeg的信息。   
消息摘要扩展为12种算法。   
解决问题：表单元失焦时应自动提交修改；检查文件冗余时抛出并发异常；添加包含大量文件的目录会使界面僵住；批处理解包7z格式失败；zip包中文件大小未知。     
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av5.9)    


2019-11-18 版本5.8  升级至jdk13+javafx13+derby15。  
Derby数据库可以在网络模式和嵌入模式之间切换。提醒：在一些机器上启动和关闭Derby网络模式都非常慢。   
可对文件或输入文本生成消息摘要。支持：MD5/SHA1/SHA256。   
文件的归档/压缩/解压/解档。支持：zip, tar, 7z, ar, cpio, gzip, bzip2, xz, lzma, Pack200, DEFLATE, snappy-framed, lz4-block, lz4-framed等。  
检查文件冗余：根据MD5查找重复的文件，提供树状结构方便用户删除冗余文件，可边查边删。  
html与Markdown之间批量互换。   
解决问题：多个界面上有失效控件；无内容页面会阻塞微博截图工具；图像混合时还要考虑背景透明的情形；图像“换色”功能失效。   
《开发者指南》v2.0。  
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av5.8)    


2019-10-26 版本5.7  编辑Markdown。同步转换html和Markdown。   
增加图像量化的算法并用于分析图象：K-Means适用于计算最不同的颜色，统计量化适用于计算出现最多的颜色。图象数据可以保存为html文件。   
文件/目录操作：查找、删除、复制、移动，并改进重命名功能。   
改进批量处理时选择文件的方式：扩展名、文件名、文件大小、文件修改时间，支持正则表达式。   
改进多个工具的界面以平衡控件的布局。   
改进微博截图工具：提高在高清屏幕上截图的分辨率；截图保存为文件以免内存溢出。   
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av5.7)    


2019-10-01 版本5.6 配置文件仍定位于用户目录。   
对于OCR，图像预处理选项：九种增强算法、伸缩比例、黑白阈值、旋转角度、是否自动矫正偏斜、是否反色。
识别选项：数据文件列表及其顺序、是否生成“区域”/“词”数据及其粒度。
批量识别选项：是否生成html或PDF、是否合并识别出文字。
内置英文和中文的“最快的”数据文件，在windows上解包即用。    
生成13种一维码和3种二维码。一维码选项：朝向、宽高、分辨率、文字位置、字体大小、空白区宽度等。
二维码选项：宽高、边沿、纠错级别、压缩模式。不同类型的选项不同。
二维码QR_Code可以在中心显示一个图片。根据纠错级别自动调整图片大小。   
解码9种一维码和3种二维码，显示条码内容和元数据（条码类型、纠错级别等）。   
调色盘：可命名颜色；增加显示cmyk值和cie值。        
生日快乐，中国！  
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av5.6)    


2019-9-19 版本5.5 基于tess4j支持识别图像和PDF中的文字。单图识别可选择矩形区域。PDF批量识别可设置转换图像的色彩空间和像素密度。目前只限Windows，并且用户需要下载数据文件。   
生成windows/linux/mac的自包含程序包。    
优化代码：只用maven打包而脱离对java 8的依赖；利用最新jpackage制作自包含包。    
修正问题：上一版本中微博截图工具挂了；在mac上微博截图工具首次运行后再也无法使用；linux上点击链接则程序僵死；计算CIELuv和CIELab时不应该归一化。   
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av5.5)    

2019-9-15 版本5.4  “数据目录”改为“运行目录”而不是以前的“用户目录”。用配置文件来保存基础参数。   
在线修改运行参数：最大可用内存、是否关闭分辨率感知、数据目录。修改这几个参数将会使MyBox自动重启。   
基于pdf2dom，以网页模式查看PDF页面。批量把PDF转换为网页。    
重构图像处理的界面：左右幕布式区域、上下风箱式菜单、多页签切换目标、子功能区更细化的显示/隐藏/调整。“按需可见”。   
粘贴板：保存多个来源的图片以供粘贴，在图片上拖拉来调整位置和大小，可选混合模式，可旋转被粘贴图片。提供示例图片。  
调色盘：可保存上千种色彩，可自动填写139种常用色彩，可导出为html页面，可在当前图片、图片历史、或参照图上点击取色。  
新的范围类型“轮廓”：把背景透明的图片的轮廓自动提取出来，作为操作的范围。提供示例图片。   
保存和管理图像处理的"范围"：增、删、改、清除，应用已保存的范围。   
统一快捷键，并提供帮助页面。  
优化代码：用公开的API替换掉内部类引用。确保单例程任务互斥进入和干净退出。写文件时先写到临时文件中以免意外导致源文件损害。        
修正问题。上一版本中以下工具失效：“修改PDF属性”、“压缩PDF”、“分割PDF”。阴影处理和3种混合模式中遗漏对于透明像素的处理。      
 [此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av5.4)    
   
2019-8-8 版本5.3 迁移至： netbeans 11 + Java 12。    
优化批量处理界面：可添加目录、展开目录、过滤文件名、选择如何处理重名文件。    
优化图像转换：可选择更多的颜色空间并支持引用外部ICC特性文件、可选图像嵌入ICC特性文件、可选对透明通道的处理。    
优化图像元数据的解码：可读取图像中嵌入的ICC特性文件。    
优化代码：利用匿名类和嵌入fxml尽可能减少重复代码；整理类继承的关系；使项目配置文件支持多平台构建。    
初版《开发指南》。    
修正问题：”图像处理-颜色-透明度“的预乘透明算法用错了；在linux上另存图像时未自动添加扩展名而导致保存失败；    
linux上无法打开链接；ICC特性文件版本解码/编码错误、数据太多时界面会僵住、未解码的数据会导致xml无法生成。    
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av5.3)    
    
2019-6-30 版本5.2 图像解码：可读Adobe YCCK/CMYK的jpg图像；读取和显示多帧图像文件中所有图像的属性和元数据。    
PDF工具：标签（目录）和缩略图；可修改PDF文件的属性，如作者、版本、用户密码、用户权限、所有者密码等。    
编辑矩阵数据：适应带格式的输入数据；自动把当前矩阵数据转变为行向量、列向量、或指定列数的矩阵；    
自动生成单位矩阵、随机方阵、或随机矩阵。    
矩阵一元计算，包括转置、行阶梯形、简化行阶梯形、行列式值-用消元法求解、行列式值-用余子式求解、逆矩阵-用消元法求解、    
逆矩阵-用伴随矩阵求解、矩阵的秩、伴随矩阵、余子式、归一化、设置小数位数、设为整型、乘以数值、除以数值、幂。    
矩阵二元计算，包括加、减、乘、克罗内克积、哈达马积、水平合并、垂直合并。    
色彩空间的工具：绘制色度图；编辑ICC色彩特性文件；RGB色彩空间基色的色适应；线性RGB与XYZ之间的转换矩阵；    
线性RGB到线性RGB的转换矩阵；颜色值的色适应；标准光源；色度适应矩阵。    
修正问题：微博截图工具经常“414 Request-URI Too Large”；按钮的提示在屏幕边沿闪烁；一些链接不可用。    
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av5.2)    
   
2019-5-1 版本5.1 界面：控件显示为图片，5种颜色可选，可选是否显示控件文字。    
简化小提示，以适应14英寸的笔记本屏幕。    
图像工具：提取/添加透明通道。    
修正若干问题，包括：图像处理中过滤透明像素的错误条件。    
劳动节快乐！    
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av5.1)    
    
2019-4-21 版本5.0 以拖拉锚点的方式选择图像操作的区域。    
“涂鸦”：在图像上粘贴图片、添加形状（矩形/圆形/椭圆/多边形）的线条或填充形状、绘制多笔一线或一笔一线。    
画笔的宽度、颜色、实虚可选。    
查看图像：设置加载宽度；选择显示坐标和标尺；旋转可保存。    
浏览图像：缩略图格栅模式、缩略图列表模式、文件列表模式；可设置加载宽度；旋转可保存。    
图像处理：抖动处理扩展到除抠图以外的所有范围；利用预乘透明技术使不支持Alpha通道的格式也可展示透明效果；    
模糊边沿；低层实现阴影效果；拖动锚点以修改大小或边沿；多种形状的剪裁；文字可垂直。    
界面：只显示有用控件；足够但不叨扰的提示信息；快捷键/主键/缺省键；实时监视内存/CPU状态；    
查看JVM属性；刷新/重置窗口；保存和恢复界面尺寸；弹出最近访问的文件/目录；记录最近使用的工具。    
代码重构：以子类而不是分支语句来实现选择逻辑、把判断移至循环外；循环中避免浮点计算；理顺继承关系、减少重复代码；    
统一管理窗口的打开和关闭、避免线程残留。    
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av5.0)    
    
2019-2-20 版本4.9 图像对比度处理，可选算法。颜色量化时可选是否抖动处理。    
图像的统计数据分析，包含各颜色通道的均值/方差/斜率/中值/众数以及直方图。    
系统粘贴板内图像的记录工具。    
随时修改界面字体。    
查看图像：可选择区域来剪裁、复制、保存。    
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av4.9)    

    
2019-1-29 版本4.8 以图像模式查看PDF文件，可以设置dpi以调整清晰度，可以把页面剪切保存为图片。    
文本/字节编辑器的“定位”功能：跳转到指定的字符/字节位置、或跳转到指定的行号。    
切割文件：按文件数、按字节数、或按起止列表把文件切割为多个文件。    
合并文件：把多个文件按字节合并为一个新文件。    
程序可以跟一个文件名作为参数，以用MyBox直接打开此文件。    
在Windows上可以把图片/文本/PDF文件的打开方式缺省关联到MyBox.exe，可以在以双击文件时直接用MyBox打开。    
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av4.8)    
    
2019-1-15 版本4.7  编辑字节：常用ASCII字符的输入选择框；按字节数、或按一组字节值来换行；查找与替换，本页或整个文件，计数功能；    
行过滤，“包含任一”、“不含所有”、“包含所有”、“不含任一”，累加过滤，保存过滤结果，是否包含行号；    
选择字符集来解码，同步显示、同步滚动、同步选择；    
分页，可用于查看和编辑非常大的文件，如几十G的二进制文件，设置页尺寸，对于跨页字节组，确保查找、替换、过滤的正确性。    
批量改变文件的换行符。    
合并“文件重命名”和“目录文件重命名”。    
图像模糊改为“平均模糊”算法，它足够好且更快。    
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av4.7)    
    
2018-12-31 版本4.6  编辑文本：自动检测换行符；转换换行符；支持LF（Unix/Linux）、CR（iOS）、CRLF（Windows）。    
查找与替换，可只本页查找、或整个文件查找。    
行过滤，匹配类型：“包含字串之一”、“不包含所有字串”，可累加过滤，可保存过滤结果。    
分页：可用于查看和编辑非常大的文件，如几十G的运行日志；可设置页尺寸；对于跨页字符串确保查找、替换、过滤的正确性。    
先加载显示首页，同时后端扫描文件以统计字符数和行数；统计期间部分功能不可用；统计完毕自动刷新界面。    
进度等待界面添加按钮“MyBox”和“取消”，以使用户可使用其它功能、或取消当前进程。    
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av4.6)    
    
2018-12-15 版本4.5 文字编码：自动检测或手动设置文件编码；设置目标文件编码以实现转码；支持BOM设置；    
十六进制同步显示、同步选择；显示行号。批量文字转码。    
图像分割支持按尺寸的方式。    
可将图像或图像的选中部分复制到系统粘贴板（Ctrl-c）。    
在查看图像的界面可裁剪保存。    
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av4.5)    
    
2018-12-03 版本4.4 多帧图像文件的查看、提取、创建、编辑。支持多帧Tiff文件。    
对于所有以图像文件为输入的操作，处理多帧图像文件的情形。    
对于所有以图像文件为输入的操作，处理极大图像（加载所需内存超出可用内存）的情形。自动评估、判断、给出提示信息和下一步处理的选择。    
对于极大图像，支持局部读取、边读边写的图像分割，可保存为多个图像文件、多帧Tiff、或者PDF。    
对于极大图像，支持降采样。可设置采样区域和采样比率。    
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av4.4)    
    
2018-11-22 版本4.3 支持动画Gif。查看动画Gif：设置间隔、暂停/继续、显示指定帧并导览上下帧。    
提取动画Gif：可选择起止帧、文件类型。    
创建/编辑动画Gif：增删图片、调整顺序、设置间隔、是否循环、选择保持图片尺寸、或统一设置图片尺寸、另存，所见即所得。    
更简洁更强力的图像处理“范围”：全部、矩形、圆形、抠图、颜色匹配、矩形中颜色匹配、圆形中颜色匹配；    
颜色匹配可针对：红/蓝/绿通道、饱和度、明暗、色相；可方便地增减抠图的点集和颜色列表；均可反选。    
归并图像处理的“颜色”、“滤镜”、“效果”、“换色”，以减少界面选择和用户输入。    
多图查看界面：可调整每屏文件数；更均匀地显示图片。    
[此版本关闭的需求/问题列表](http://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+milestone%3Av4.3)    
    
2018-11-13 版本4.2 图像处理的“范围”：全部、抠图、矩形、圆形、色彩匹配、色相匹配、矩形/圆形累加色彩/色相匹配。    
“抠图”如PhotoShop的魔术棒或者windows画图的油漆桶。    
“范围”可应用于：色彩增减、滤镜、效果、卷积、换色。可简单通过左右键点击来确定范围。    
卷积管理器：可自动填写高斯分布值；添加处理边缘像素的选项。    
目录重命名：可设置关键字列表来过滤要处理的文件。    
调整和优化图像处理的代码。    
更多的快捷键。    
    
2018-11-08 版本4.1 图像的“覆盖”处理。可在图像上覆盖：矩形马赛克、圆形马赛克、矩形磨砂玻璃、圆形磨砂玻璃、或者图片。    
可设置马赛克或磨砂玻璃的范围和粒度；可选内部图片或用户自己的图片；可设置图片的大小和透明度。    
图像的“卷积”处理。可选择卷积核来加工图像。可批量处理。    
卷积核管理器。自定义（增/删/改/复制）图像处理的卷积核，可自动归一化，可测试。提供示例数据。    
图像滤镜：新增黄/青/紫通道。    
    
2018-11-04 版本4.0  图像色彩调整：新增黄/青/紫通道。尤其黄色通道方便生成“暖色”调图片。    
图像滤镜：新增“褐色”。可以生成怀旧风格的图片。    
图像效果：新增“浮雕”，可以设置方向、半径、是否转换为灰色。    
图像的混合：可设置图像交叉位置、可选择多种常用混合模式。    
在线帮助：新增一些关键信息。    
    
2018-10-26 版本3.9  内嵌Derby数据库以保存程序数据；确保数据正确从配置文件迁移到数据库。    
图像处理：保存修改历史，以便退回到前面的修改；用户可以设置历史个数。    
用户手册的英文版。    
    
2018-10-15 版本3.8 优化代码：拆分图像处理的大类为各功能的子类。    
优化界面控件，使工具更易使用。设置快捷键。    
图像处理添加三个滤镜：红/蓝/绿的单通道反色。水印文字可以设置为“轮廓”。    
    
2018-10-09 版本3.7 微博截图工具：利用Javascript事件来依次加载图片，确保最小间隔以免被服务器判定为不善访问，    
同时监视最大加载间隔以免因图片挂了或者加载太快未触发事件而造成迭代中断。    
图像处理“效果”：模糊、锐化、边沿检测、海报（减色）、阈值化。    
    
2018-10-04 版本3.6 微博截图工具：继续调优程序逻辑以确保界面图片全部加载；整理代码以避免内存泄露。    
降低界面皮肤背景的明亮度和饱和度。    
在文档中添加关于界面分辨率的介绍。    
    
2018-10-01 版本3.5 微博截图工具：调优程序逻辑，以确保界面图片全部加载。    
提供多种界面皮肤。    
    
2018-09-30 版本3.4 修正问题：1）微博截图工具，调整页面加载完成的判断条件，以保证页面信息被完整保存。    
2）关闭/切换窗口时若任务正在执行，用户选择“取消”时应留在当前窗口。    
新增功能：1）可以设置PDF处理的最大主内存和临时文件的目录；2）可以清除个人设置。    
    
2018-09-30 版本3.3 最终解决微博网站认证的问题。已在Windows、CentOS、Mac上验证。    
    
2018-09-29 版本3.2 微博截图功能：1）在Linux和Windows上自动导入微博证书而用户无需登录可直接使用工具。    
但在Mac上没有找到导入证书的途径，因此苹果用户只好登录以后才能使用。    
2）可以展开页面上所有评论和所有图片然后截图。    
3）可以将页面中所有图片的原图保存下来。（感觉好酷）    
    
2018-09-26 版本3.1 所有图像操作都可以批量处理了。修正颜色处理算法。    
设置缺省字体大小以适应屏幕分辨率的变化。用户手册拆分成各个工具的分册了。    
提示用户：在使用微博截图功能之前需要在MyBox浏览器里成功登录一次以安装微博证书、    
（正在寻求突破这一限制的办法。Mybox没有兴趣接触用户个人信息）。    
    
2018-09-18 版本3.0 微博截图工具：可以只截取有效内容（速度提高一倍并且文件大小减小一半）、    
可以展开评论（好得意这个功能！）、可以设置合并PDF的最大尺寸。    
修正html编辑器的错误并增强功能。    
    
2018-09-17 版本2.14 微博截图工具：设置失败时重试次数、以应对网络状况很糟的情况；    
当某个月的微博页数很多时，不合并当月的PDF文件，以避免无法生成非常大的PDF文件的情况（有位博主一个月发了36页微博~）。。    
    
2018-09-15 版本2.13 分开参照图和范围图。确保程序退出时不残留线程。批量PDF压缩图片。    
微博截图工具：自动保存任意微博账户的所有微博内容，可以设置起止月份，可以截图为PDF、也可以保存html文件    
（由于微博是动态加载内容，本地网页无法正常打开，仅供获取其中的文本内容）。    
如果微博修改网页访问方式，此工具将可能失效。    
    
2018-09-11 版本2.12 合并多个图片为PDF文件、压缩PDF文件的图片、合并PDF、分割PDF。     
支持PDF写中文，程序自动定位系统中的字体文件，用户也可以输入ttf字体文件路径。     
提示信息的显示更平滑友好。网页浏览器：字体缩放，设置截图延迟、截图可保存为PDF。    
    
2018-09-06 版本2.11 图片的合并，支持排列选项、背景颜色、间隔、边沿、和尺寸选项。    
网页浏览器，同步网页编辑器，把网页完整内容保存为一张图片。图片处理：阴影、圆角、加边。    
确保大图片处理的正确性和性能。    
    
2018-08-11 版本2.10 图像的分割，支持均等分割个和定制分割。使图像处理的“范围”更易用。    
同屏查看多图不限制文件个数了。    
    
2018-08-07 版本2.9 图像的裁剪。图像处理的“范围”：依据区域（矩形或圆形）和颜色匹配，可用于局部处理图像。    
    
2018-07-31 版本2.8 图像的切边、水印、撤销、重做。Html编辑器、文本编辑器。    
    
2018-07-30 版本2.7 图像的变形：旋转、斜拉、镜像。    
    
2018-07-26 版本2.6 增强图像的换色：可以选择多个原色，可以按色彩距离或者色相距离来匹配。支持透明度处理。    
    
2018-07-25 版本2.5 调色盘。图像的换色：可以精确匹配颜色、或者设置色距，此功能可以替换图像背景色、或者清除色彩噪声。    
    
2018-07-24 版本2.4 完善图像处理和多图查看：平滑切换、对照图、像素调整。    
    
2018-07-18 版本2.3 闹钟，包括时间选项和音乐选项，支持wav铃音和MP3铃音，可以在后端运行。感谢我家乖乖贡献了“喵”。    
    
2018-07-11 版本2.2 修正线程处理逻辑的漏洞。整理文件，将文件按修改时间或者生成时间重新归类在新目录下。    
此功能可用于处理照片、游戏截图、和系统日志等需要按时间归档的批量文件。    
    
2018-07-09 版本2.1 完善图片处理的界面，支持导览。    
目录同步，包含复制子目录、新文件、特定时间以后已修改文件、原文件属性，以及删除源目录不存在文件和目录，等选项。    
    
2018-07-06 版本2.0 批量提取PDF文字、批量转换图片。    
目录文件重命名，包含文件名和排序的选项，被重命名的文件可以全部恢复或者指定恢复原来的名字。    
    
2018-07-03 版本1.9 修正问题。提取PDF文字时可以定制页分割行。    
完善图像处理：参数化调整饱和度、明暗、色相；滤镜：灰色、反色、黑白色。    
    
2018-07-01 版本1.8 将PDF文件中的文字提取出来。处理图片：调整饱和度、明暗，或者转换为灰色、反色。    
    
2018-06-30 版本1.7 完善像素计算器。支持同屏查看最多十张图，可以分别或者同步旋转和缩放。    
    
2018-06-27 版本1.6 将图片转换为其它格式，支持色彩、长宽、压缩、质量等选项。    
提供像素计算器。新增图像格式：gif, wbmp, pnm, pcx。    
    
2018-06-24 版本1.5 提取PDF中的图片保存为原格式。    
支持批量转换和批量提取。感谢 “https://shuge.org/” 的帮助：书格提出提取PDF中图片的需求。    
    
2018-06-21 版本1.4 读写图像的元数据,目前支持图像格式：png, jpg, bmp, tif。    
感谢 “https://shuge.org/” 的帮助：书格提出图像元数据读写的需求。    
    
2018-06-15 版本1.3 修正OTSU算法的灰度计算；优化代码：提取共享部件；支持PDF密码；使界面操作更友好。    
    
2018-06-14 版本1.2 针对黑白色添加色彩转换的选项；自动保存用户的选择；优化帮助文件的读取。    
感谢 “https://shuge.org/” 的帮助：书格提出二值化转换阈值的需求。    
    
2018-06-13 版本1.1 添加：转换格式tiff和raw，压缩和质量选项，以及帮助信息。    
感谢 “https://shuge.org/” 的帮助：书格提出tiff转换的需求。    
    
2018-06-12 版本1.0 实现功能：将PDF文件的每页转换为一张图片，包含图像密度、类型、格式等选项，并且可以暂停/继续转换过程。    

[未定义版本的已关闭的需求/问题列表](https://github.com/Mararsh/MyBox/issues?q=is%3Aissue+is%3Aclosed+no%3Amilestone)    

# 主界面

![截屏1](https://mararsh.github.io/MyBox/1.jpg)

![截屏2](https://mararsh.github.io/MyBox/2.jpg)

![截屏3](https://mararsh.github.io/MyBox/3.jpg)

![截屏4](https://mararsh.github.io/MyBox/4.jpg)

![截屏5](https://mararsh.github.io/MyBox/5.jpg)

![截屏6](https://mararsh.github.io/MyBox/6.jpg)

![截屏7](https://mararsh.github.io/MyBox/7.jpg)

![截屏8](https://mararsh.github.io/MyBox/8.jpg)

![截屏9](https://mararsh.github.io/MyBox/9.jpg)





