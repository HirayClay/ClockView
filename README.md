# ClockView <br>
简单的时钟控件，看到网上有很多时钟控件都是互相抄来抄去，时钟上的数字方向都是错的，比如6点已经倒过来变成9了
自己随手实现了一下，顺便把数字的方向摆正.自己的实现还有一个小bug：如果仔细观察，秒针并不是均匀的一秒走一下，偶尔出现一秒快速的摆了两下，应该是onDraw里面耗时的计算导致,我的想法是用一个线程不停的计算某一时刻的hour,minute和second对应的角度，onDraw的时候直接根据当前时间作为key去取角度<br>
![](https://github.com/HirayClay/ClockView/raw/master/app/static/ss.gif "still poor picture quality")<br>
