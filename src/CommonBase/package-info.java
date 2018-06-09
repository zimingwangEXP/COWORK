package CommonBase;
/*
     本部分代码提供客户端之间及客户端与服务器间的网络通信的接口函数
     同时对以下的一些通用类进行了定义:
     Log,BasicInfo,SuperInfo,Status,Remark,UserSnapShot
     waringing list:
     warning1: 在1.0版本中尚未完全实现视频，音频的实时聊天功能(存在未被解决的bug)
     warning2: Log类可能对文件的读写可能需要加锁进行互斥保护
     warning5: 阻塞式的网络操作无法适应>10000的用户负荷量
     warning3: 数据库在考虑效率的基础上存在优化的余地
     warning4: 前端UI设计问题
 */