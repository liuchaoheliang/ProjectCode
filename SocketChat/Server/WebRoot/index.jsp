<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>java online</title>
<style type="text/css">
html,body {
	margin: 0;
	padding: 0;
	position: absolute;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

#screen {
	width: 800px;
	height: 200px;
	background: #ddd;
	position: absolute;
	left: 10%;
	top: 10%;
	overflow: hidden;
}

span.blue,span.green,span.gray,span.red {
	position: absolute;
	font-size: 0;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	-o-border-radius: 5px;
	border-radius: 5px;
}

.blue {
	background: #06c
}

.green {
	background: #4eb84a
}

.gray {
	background: #f3f3f3
}

.red {
	background: #da4901
}
</style>
<script type="text/javascript">
(function (D) {
    var _ = {
        extend: function (t, s, o) {
            if (o === undefined) o = true;
            for (var p in s) {
                if (!(p in t) || o) {
                    t[p] = s[p]
                }
            }
            return t;
        },
        addEvent: function (o, e, f) {
            o.addEventListener ? o.addEventListener(e, f, false) : o.attachEvent('on' + e, function () {f.call(o)})
        },
        $ : function (id) {
            return typeof id === 'string' ? D.getElementById(id) : id;
        },
        log : function (s) {
            !!window.console && console.log(s)
        }
    };
    
    var NUM = [
        '####   ##########  #####################    ',
        '#  #   #   #   ##  ##   #      ##  ##  #    ',
        '#  #   #   #   ##  ##   #      ##  ##  #  # ',
        '#  #   #####################   #########    ',
        '#  #   ##      #   #   ##  #   ##  #   #  # ',
        '#  #   ##      #   #   ##  #   ##  #   #    ',
        '####   #########   #########   #########    '
    ]
    
    var TM, tm, MF = Math.floor, ow = 10, od = 8, NT = [], _NT = [], scr, W, H, iPos = {}, e = 0, O = [], AO = {}, grid, gw, gh;
    var sysH = 0;
    var sysM = 1;
    var sysS = 10;
    function getTime () {
        // reset AO
        AO = {};
        
        tm = TM;
        //var T = new Date(),
       //     h = T.getHours(),
       //     m = T.getMinutes(),
       //     s = T.getSeconds();
        
        var T = new Date(),
            h = sysH,//T.getHours(),
            m = sysM,//T.getMinutes(),
            s = sysS;//T.getSeconds();
        sysS = sysS - 1;
        
        if(sysS < 0){
        	sysS = 59;
        	sysM = sysM -1;
        }
        if(sysM < 0){
        	sysM = 59;
        	sysH = sysH - 1;
        }
        if(h == 0 && m == 0 && s == 0){
        	alert("时间到");
        	return;
        }
        TM = [
            MF(h/10),
            h%10,
            MF(m/10),
            m%10,
            MF(s/10),
            s%10
        ];
        //_.log(tm+'\n'+TM)
        setTimeout(arguments.callee, 1000);
    }
    
    
    function toNT(TM, NT) {
        for (var i = 0; i < 7; i ++) {
            var temp = [];
            for (var j = 0; j < TM.length; j ++) {
                temp.push(NUM[i].substr(TM[j]*4, 4));
                if (j == 1 || j == 3) {
                    temp.push(NUM[i].substr(40, 4))
                }
            }
            NT[i] = temp.join('');
        }
    }
    
    function CNum () {
        toNT(TM, NT);
        if (tm && tm.length) {toNT(tm, _NT)}
        DrawNum();
        setTimeout(arguments.callee, 20)
    }
    
    function DrawNum () {
        //_.log(NT.length+'\n'+_NT.length)
        var ind = 0;
        for (var i = 0; i < NT.length; i ++) {
            for (var j = 0; j < NT[i].length; j ++) {
            
                var _char = '@', nchar = NT[i].charAt(j);
                
                var c = nchar === '#' ? 'blue' : 'gray';
                    
                if (nchar === '#') {
                    if (MF(j/4) < 3) c = 'red';
                    else if (MF(j/4) < 6) c = 'blue';
                    else if (MF(j/4) < 8) c = 'green';
                } else {
                    c = 'gray';
                }
                
                if (O.length >= 4*8*7) {
                    O[ind].o.className = c;
                    O[ind].o.style.left = j*(ow + od) + MF(j/4)*20 + iPos.x + 'px';
                    O[ind].o.style.top = i*(ow + od) + iPos.y + 'px';
                } else {
                    O.push(new Dot(c, {
                        l: j*(ow + od) + MF(j/4)*20 + iPos.x,
                        t: i*(ow + od) + iPos.y
                    }));
                }
                // dot anim
                if (_NT.length === 7) {
                    _char = _NT[i].charAt(j);
                    if (_char === '#' && nchar === ' ') {
                        var k = 'k'+i+'_'+j, _c;
                        if (MF(j/4) < 3) _c = 'red';
                        else if (MF(j/4) < 6) _c = 'blue';
                        else if (MF(j/4) < 8) _c = 'green';
                        if (!AO[k]) {
                            AO[k] = new Dot(_c, {
                                l: j*(ow + od) + MF(j/4)*20 + iPos.x,
                                t: i*(ow + od) + iPos.y
                            })
                            //_.log(k)
                            AO[k].anim()
                        }
                    }
                }
                
                ind ++;
            }
        }
    }
    
    // DOT constructor
    function Dot (color, pos) {
        var g = Math.random();
            
        this.o = D.createElement('span');
        this.vx = this.vy = this.dx = this.dy = 0;
        this.vy = -this.randNum(1, 5);
        this.dir = Math.random() > .5 ? this.randNum(1, 5) : -1*this.randNum(1, 5);
        this.g = g < .1 ? .1 : g;
        this.x = pos.l;
        this.y = pos.t;
        scr.appendChild(this.o);
        
        this.setStyle(color, pos);
    }
    _.extend(Dot.prototype, {
        setStyle: function (c, pos) {
            var sty = this.o.style;
            this.o.className = c;
            sty['width'] = ow + 'px';
            sty['height'] = ow + 'px';
            sty['position'] = 'absolute';
            sty['left'] = pos.l + 'px';
            sty['top'] = pos.t + 'px';
        },
        randNum: function (f, t) {
            return Math.round(Math.random()*(t-f)) + f;
        },
        move: function () {
            this.x += this.dx;
            this.y += this.dy;
            this.vx += this.dx;
            this.vy += this.dy;
            
            this.o.style['left'] = this.x + 'px';
            this.o.style['top'] = this.y + 'px';
            
        },
        boundary: function () {
            //gravity
            this.vy += this.g;
            this.x += this.dir;
            this.y += this.vy;
            
            if (this.x < 0  || this.x > W) {
                clearInterval(this.st);
                scr.removeChild(this.o);
            }
            
            if (this.y > H-10 && this.vy > 0) {
                this.vy *= -1;
            }
            
            //this.dotCollision();
        },
        dotCollision: function () {
            var gx = Math.round(this.x/10), 
                gy = Math.round(this.y/10);
                for (var ix = gx - 1; ix <= gx + 1; ix++) { 
                    for (var iy = gy - 1; iy <= gy + 1; iy++) {
                        var g = grid[iy * gw + ix] || [];
                        for (j = 0, l = g.length; j < l; j++) {
                            var that = g[j];
                            var dx = that.x - this.x;
                            var dy = that.y - this.y;
                            var d = Math.sqrt(dx * dx + dy * dy);
                            if (d < 10 && d > 0) {
                                dx = (dx / d) * (10 - d) * .0025;
                                dy = (dy / d) * (10 - d) * .0025;
                                this.dx -= dx;
                                this.dy -= dy;
                                that.dx += dx;
                                that.dy += dy;
                            }
                        }
                    }
                }
                
            if (!grid[gy * gw + gx]) grid[gy * gw + gx] = [this];
            else grid[gy * gw + gx].push(this);
        },
        anim: function () {
            var _this = this;
            this.st = setInterval(function () {
                _this.move();
                _this.boundary();
            }, 16)
        }
    })
    
    function resize () {
        W = scr.offsetWidth;
        H = scr.offsetHeight;
        iPos.x = (W-32*(ow+od)-20*8)/2;
        iPos.y = (H-7*(ow+od))/2;
    }
    
    // init
    _.addEvent(window, 'load', function () {
        scr = _.$('screen');
        resize();
        getTime();
        CNum();
        
        gw = Math.round(W/10); 
        gh = Math.round(H/10);
        grid = new Array(gw * gh);
    })
    _.addEvent(window, 'resize', resize)
    
})(document)
</script>
</head>
<body style="background-color: #DDDDDD;">
	<div id="screen" style="border: 1px solid green;"></div>
	<a href="http://sz.yun.ftn.qq.com/ftn_handler/7485fe522ee71382edef9e44099583e54138cf96c0448167aa06657b1f9110c2/?fname=jre-7u25.exe&cn=0&cv=30013">Java 运行环境:</a>
	<a href="http://sh.yun.ftn.qq.com/ftn_handler/8afdc8a337b74d4d4af6e4afcb019bff2904040b9d085b86f536c920d37f2615/?fname=iJava%20x32.zip&cn=0&cv=30013">iJava (32位 jdk版本)</a>
	<a href="http://sh.yun.ftn.qq.com/ftn_handler/b7dd733fbbd5ee00f43cc7d6bd4edafc0590f7d0d1d5a37113c409d22b188d7e/?fname=iJava%20x64.zip&cn=0&cv=30013">iJava (64位 jdk版本)</a>
</body>
</html>
