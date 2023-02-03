let motion1 = 'null';
let motion2 = 'null';
let motion3 = 'null';
let visiChara1 = 'hidden';
let visiChara2 = 'hidden';
let visiChara3 = 'hidden';
let visifuki1 = 'hidden';
let visifuki2 = 'hidden';
let visifuki3 = 'hidden';
let time1 = 0;
let time2 = 0;
let time3 = 0;


let mode1 = 0;
let mode2 = 0;
let mode3 = 0;

let activeMode = 1;
var motionWait = {
  1: function (chara) { purun(chara); },
  2: function (chara) { pyon(chara); },
  3: function (chara) { poyoon(chara); },
  4: function (chara) { purupurun(chara); },
  5: function (chara) { pururun(chara); },
  6: function (chara) { puyon(chara); },
  7: function (chara) { papa(chara); },
  8: function (chara) { korokoro(chara); },
};

var motionClick = {
  1: function (chara) { bunshin(chara); },
  2: function (chara) { jetto(chara); },
  3: function (chara) { look(chara); },
  4: function (chara) { flow(chara); },
};
let count = 1;
let charaMode1 = "wait";
let charaMode2 = "wait";
let charaMode3 = "wait";


//背景の挙動設定
// document.querySelector(`.container`).animate(
//   [
//     { backgroundPosition: '0 0' },
//     { backgroundPosition: '-100% 0' }
//   ],
//   {
//     duration: 5000,
//     iterations: Infinity
//   }
// );


// CSSアニメーションを間隔を空けてループ再生させる処理
function looopAnimation(id, className) {
  var element = document.getElementById(id);
  element.className = className;
  // element.addEventListener("animationend", listener);

  // function listener(event) {
  //   event.target.classList.remove(className);
  //   setTimeout(playAnimation, delay);
  // }

  // function playAnimation() {
  //   element.className = className;
  // }

}


/**
 * ホーム画面（待機モーション）
 */
function purun(chara) {
  if (chara == 'chara1') {
    anim(chara, 'motion1', "purun", motion1);
  } else if (chara == 'chara2') {
    anim(chara, 'motion2', "purun", motion2);
  } else if (chara == 'chara3') {
    anim(chara, 'motion3', "purun", motion3);
  }
}

/**
 * 予定追加画面
 */
function korokoro(chara) {
  if (chara == 'chara1') {
    anim(chara, 'motion1', "korokoro", motion1);
  } else if (chara == 'chara2') {
    anim(chara, 'motion2', "korokoro", motion2);
  } else if (chara == 'chara3') {
    anim(chara, 'motion3', "korokoro", motion3);
  }

}

function pyon(chara) {
  if (chara == 'chara1') {
    anim(chara, 'motion1', "pyon", motion1);
  } else if (chara == 'chara2') {
    anim(chara, 'motion2', "pyon", motion2);
  } else if (chara == 'chara3') {
    anim(chara, 'motion3', "pyon", motion3);
  }

}

function poyoon(chara) {
  if (chara == 'chara1') {
    anim(chara, 'motion1', "poyoon", motion1);
  } else if (chara == 'chara2') {
    anim(chara, 'motion2', "poyoon", motion2);
  } else if (chara == 'chara3') {
    anim(chara, 'motion3', "poyoon", motion3);
  }

}

function purupurun(chara) {
  if (chara == 'chara1') {
    anim(chara, 'motion1', "purupurun", motion1);
  } else if (chara == 'chara2') {
    anim(chara, 'motion2', "purupurun", motion2);
  } else if (chara == 'chara3') {
    anim(chara, 'motion3', "purupurun", motion3);
  }

}

function pururun(chara) {
  if (chara == 'chara1') {
    anim(chara, 'motion1', "pururun", motion1);
  } else if (chara == 'chara2') {
    anim(chara, 'motion2', "pururun", motion2);
  } else if (chara == 'chara3') {
    anim(chara, 'motion3', "pururun", motion3);
  }

}

function puyon(chara) {
  if (chara == 'chara1') {
    anim(chara, 'motion1', 'puyon', motion1);
  } else if (chara == 'chara2') {
    anim(chara, 'motion2', 'puyon', motion2);
  } else if (chara == 'chara3') {
    anim(chara, 'motion3', 'puyon', motion3);
  }

}


function papa(chara) {
  if (chara == 'chara1') {
    anim(chara, 'motion1', 'papa', motion1);
  } else if (chara == 'chara2') {
    anim(chara, 'motion2', 'papa', motion2);
  } else if (chara == 'chara3') {
    anim(chara, 'motion3', 'papa', motion3);
  }
}

function kurukuru(chara) {
  if (chara == 'chara1') {
    anim(chara, 'motion1', 'kurukuru', motion1);
  } else if (chara == 'chara2') {
    anim(chara, 'motion2', 'kurukuru', motion2);
  } else if (chara == 'chara3') {
    anim(chara, 'motion3', 'kurukuru', motion3);
  }
  oneOffClick(chara);
  setTimeout(() => {
    let ele = document.getElementById(chara);
    ele.classList.remove(...ele.classList);
    oneOnClick(chara);
    korokoro(chara);
  }, 3000);
}

function flow(chara) {
  if (chara == 'chara1') {
    anim(chara, 'motion1', "flow", motion1);
  } else if (chara == 'chara2') {
    anim(chara, 'motion2', "flow", motion2);
  } else if (chara == 'chara3') {
    anim(chara, 'motion3', "flow", motion3);
  }
}

function jetto(chara) {
  if (chara == 'chara1') {
    anim(chara, 'motion1', "jetto", motion1);
  } else if (chara == 'chara2') {
    anim(chara, 'motion2', "jetto", motion2);
  } else if (chara == 'chara3') {
    anim(chara, 'motion3', "jetto", motion3);
  }
}

function look(chara) {
  if (chara == 'chara1') {
    anim(chara, 'motion1', "look1", motion1);
  } else if (chara == 'chara2') {
    anim(chara, 'motion2', "look2", motion2);
  } else if (chara == 'chara3') {
    anim(chara, 'motion3', "look3", motion3);
  }
}

function bunshin(chara) {
  if (chara == 'chara1') {
    anim(chara, 'motion1', "bunshin1", motion1);
  } else if (chara == 'chara2') {
    anim(chara, 'motion2', "bunshin2", motion2);
  } else if (chara == 'chara3') {
    anim(chara, 'motion3', "bunshin3", motion3);
  }

}

function allOffClick() {
  let ele1 = document.getElementById('chara1');
  ele1.removeAttribute('onclick');
  let ele2 = document.getElementById('chara2');
  ele2.removeAttribute('onclick');
  let ele3 = document.getElementById('chara3');
  ele3.removeAttribute('onclick');
}

function allOnClick() {
  let ele1 = document.getElementById('chara1');
  let ele2 = document.getElementById('chara2');
  let ele3 = document.getElementById('chara3');
  ele1.setAttribute('onclick', "clickMove('chara1')");
  ele2.setAttribute('onclick', "clickMove('chara2')");
  ele3.setAttribute('onclick', "clickMove('chara3')");
}

function oneOnClick(chara) {
  let ele = document.getElementById(chara);
  ele.setAttribute('onclick', "clickMove('" + chara + "')");
}

function oneOffClick(chara) {
  let ele = document.getElementById(chara);
  ele.removeAttribute('onclick');
}

function cMotionDelete(chara) {
  let ele = document.getElementById(chara);
  ele.classList.remove(...ele.classList);
  allOnClick();
}

function onHidden(params) {
  console.log("クローン廃棄");
  let on = document.getElementById(params);
  on.classList.remove(...on.classList);
  on.style.visibility = 'hidden';
}

function anim(chara, motion, motionName) {
  let anima = 0
  if (chara == 'chara1') {
    if (mode1 > 0) {
      clearInterval(mode1);
    }
  }
  if (chara == 'chara2') {
    if (mode2 > 0) {
      clearInterval(mode2);
    }
  }
  if (chara == 'chara3') {
    if (mode3 > 0) {
      clearInterval(mode3);
    }
  }
  let ori = document.getElementById(chara);
  let ele = ori.classList.length;
  if (ele != 0) {
    stop(chara);
    document.getElementById(chara).addEventListener('animationend', () => {
      if (anima == 0) {
        anima = 1;
        document.getElementById(chara).classList.remove(motion);
        looopAnimation(chara, motionName);
        if (motionName == "bunshin1" || motionName == "bunshin2" || motionName == "bunshin3") {
          motinclone(chara);
        }
        if (!activeMode) {
          setTimeout(cMotionDelete, 11000, chara);
          activeMode = 1;
        }
        if (motion == 'motion1') {
          motion1 = motionName;
        }
        if (motion == 'motion2') {
          motion2 = motionName;
        }
        if (motion == 'motion3') {
          motion3 = motionName;
        }

      }
    });
  } else if (ele == 0) {
    if (anima == 0) {
      anima = 1;
      document.getElementById(chara).classList.remove(motion);
      looopAnimation(chara, motionName);
      if (motionName == "bunshin1" || motionName == "bunshin2" || motionName == "bunshin3") {
        motinclone(chara);
      }
      if (!activeMode) {
        setTimeout(cMotionDelete, 11000, chara);
        activeMode = 1;
      }
      if (motion == 'motion1') {
        motion1 = motionName;
      }
      if (motion == 'motion2') {
        motion2 = motionName;
      }
      if (motion == 'motion3') {
        motion3 = motionName;
      }
    }
  }
  let run = Math.floor(Math.random() * 10000 + 12000);
  console.log(chara + '待機時間' + run);
  //待機処理
  if (chara == 'chara1') {
    mode1 = setInterval(waitMove, run, chara);
  }
  if (chara == 'chara2') {
    mode2 = setInterval(waitMove, run, chara);
  }
  if (chara == 'chara3') {
    mode3 = setInterval(waitMove, run, chara);
  }
}

function motinclone(chara) {
  console.log("クローン処理開始");
  let clone = document.getElementById(chara + 'clone');
  clone.style.visibility = "visible";
  looopAnimation(clone.id, 'breakout');
  setTimeout(onHidden, 9000, clone.id);
}

//挿入準備
function setVisible(chara) {
  const ob = document.getElementById(chara);
  if (chara == 'chara1') {
    visiChara1 = 'visible';
  }
  if (chara == 'chara2') {
    visiChara2 = 'visible';
  }
  if (chara == 'chara3') {
    visiChara3 = 'visible';
  }
  ob.style.visibility = "visible";
}

//御手洗挿入
function mitarashi() {
  document.getElementById("chara1").src = "images/mitarashi.png";
  document.getElementById("chara1clone").src = "images/mitarashi.png";
}

function tako() {
  document.getElementById("chara1").src = "images/tako.png";
  document.getElementById("chara1clone").src = "images/tako.png";
}

function neko() {
  document.getElementById("chara2").src = "images/neko.png";
  document.getElementById("chara2clone").src = "images/neko.png";
}
function neko2() {
  document.getElementById("chara2").src = "images/neko2.png";
  document.getElementById("chara2clone").src = "images/neko2.png";
}
function neko3() {
  document.getElementById("chara2").src = "images/neko3.png";
  document.getElementById("chara2clone").src = "images/neko3.png";
}
function u_pa_() {
  document.getElementById("chara3").src = "images/u_pa_.png";
  document.getElementById("chara3clone").src = "images/u_pa_.png";
}
function u_pa_2() {
  document.getElementById("chara3").src = "images/u_pa_2.png";
  document.getElementById("chara3clone").src = "images/u_pa_2.png";
}
function donatsu() {
  document.getElementById("chara1").src = "images/donatsu.png";
  document.getElementById("chara1clone").src = "images/donatsu.png";
}

function hugeBack() {
  var ele = document.getElementById("back");
  ele.style.cssText = 'width: 100%;   height: 100%;   background: url("images/bg_house_living.jpg") no-repeat center/80%;   background-size: 410px 200px;';
  if (visiChara1 == "visible") {
    var chara1 = document.getElementById("chara1");
    chara1.style.cssText = 'visibility: visible; z-index: 30;  top: 120px;  bottom: 0;';
    var chara1clo = document.getElementById("chara1clone");
    chara1clo.style.cssText = ' z-index: 30;  top: 120px;  bottom: 0;';
  }
  if (visiChara2 == "visible") {
    var chara2 = document.getElementById("chara2");
    chara2.style.cssText = 'visibility: visible; z-index: 20;  right: 50%;  top: 100px;';
    var chara2clo = document.getElementById("chara2clone");
    chara2clo.style.cssText = ' z-index: 20;  right: 50%;  top: 100px;';
  }
  if (visiChara3 == "visible") {
    var chara3 = document.getElementById("chara3");
    chara3.style.cssText = 'visibility: visible; z-index: 10;  right: -50%;  top: 100px;';
    var chara3clo = document.getElementById("chara3clone");
    chara3clo.style.cssText = ' z-index: 10;  right: -50%;  top: 100px;';
  }

}

function smallBack() {
  var ele = document.getElementById("back");
  ele.style.cssText = 'width: 100%; height: 100px; background: url("images/bg_natural_sougen.jpg") no-repeat center/80%; background-size: 500px 100px;';

  if (visiChara1 == "visible") {
    var chara1 = document.getElementById("chara1");
    chara1.style.cssText = 'visibility: visible; z-index: 30;  top: 30px;';
    var chara1clo = document.getElementById("chara1clone");
    chara1clo.style.cssText = ' z-index: 30;  top: 30px;';
  }
  if (visiChara2 == "visible") {
    var chara2 = document.getElementById("chara2");
    chara2.style.cssText = 'visibility: visible; z-index: 20;  right: 50%;  top: 30px;';
    var chara2clo = document.getElementById("chara2clone");
    chara2clo.style.cssText = ' z-index: 20;  right: 50%;  top: 30px;';
  }
  if (visiChara3 == "visible") {
    var chara3 = document.getElementById("chara3");
    chara3.style.cssText = 'visibility: visible; z-index: 10;  right: -50%;  top: 30px;';
    var chara3clo = document.getElementById("chara3clone");
    chara3clo.style.cssText = ' z-index: 10;  right: -50%;  top: 30px;';
  }
}


function huhouShinnyuu() {
  var ele = document.getElementById("back");
  ele.style.cssText = 'width: 100%; height: 100%; background: url("images/_214558.jpg") no-repeat center/80%; background-size: 450px 100px;';

  if (visiChara1 == "visible") {
    var chara1 = document.getElementById("chara1");
    chara1.style.cssText = 'visibility: visible; z-index: 30;  top: 30px;';
    var chara1clo = document.getElementById("chara1clone");
    chara1clo.style.cssText = ' z-index: 30;  top: 30px;';
  }
  if (visiChara2 == "visible") {
    var chara2 = document.getElementById("chara2");
    chara2.style.cssText = 'visibility: visible; z-index: 20;  right: 50%;  top: 30px;';
    var chara2clo = document.getElementById("chara2clone");
    chara2clo.style.cssText = ' z-index: 20;  right: 50%;  top: 30px;';
  }
  if (visiChara3 == "visible") {
    var chara3 = document.getElementById("chara3");
    chara3.style.cssText = 'visibility: visible; z-index: 10;  right: -50%;  top: 30px;';
    var chara3clo = document.getElementById("chara3clone");
    chara3clo.style.cssText = ' z-index: 10;  right: -50%;  top: 30px;';
  }
}

function ryokan() {
  var ele = document.getElementById("back");
  ele.style.cssText = 'width: 100%; height: 100%; background: url("images/bg_ryokan_hiroen.jpg") no-repeat center/80%; background-size: 450px 100px;';

  if (visiChara1 == "visible") {
    var chara1 = document.getElementById("chara1");
    chara1.style.cssText = 'visibility: visible; z-index: 30;  top: 30px;';
    var chara1clo = document.getElementById("chara1clone");
    chara1clo.style.cssText = ' z-index: 30;  top: 30px;';
  }
  if (visiChara2 == "visible") {
    var chara2 = document.getElementById("chara2");
    chara2.style.cssText = 'visibility: visible; z-index: 20;  right: 50%;  top: 15px;';
    var chara2clo = document.getElementById("chara2clone");
    chara2clo.style.cssText = ' z-index: 20;  right: 50%;  top: 15px;';
  }
  if (visiChara3 == "visible") {
    var chara3 = document.getElementById("chara3");
    chara3.style.cssText = 'visibility: visible; z-index: 10;  right: -50%;  top: 15px;';
    var chara3clo = document.getElementById("chara3clone");
    chara3clo.style.cssText = 'z-index: 10;  right: -50%;  top: 15px;';
  }
}

function visibleBalloon(name, level, chara) {
  const putName = document.getElementById(name + 'name');
  const putLevel = document.getElementById(name + 'level');
  const ob = document.getElementById(name + 'com');
  ob.style.visibility = 'visible';
  if (name == 'chara1') {
    visifuki1 = 'visible';
  }
  if (name == 'chara2') {
    visifuki2 = 'visible';
  }
  if (name == 'chara3') {
    visifuki3 = 'visible';
  }

  putName.textContent = '名前：' + chara + "くん";
  putLevel.textContent = '現在レベル：' + level;

}

function invisibleBalloon() {
  if (visifuki1 == 'visible') {
    visifuki1 = 'hidden';
    const ob = document.getElementById('chara1com');
    ob.style.visibility = 'hidden';

  }
  if (visifuki2 == 'visible') {
    visifuki2 = 'hidden';
    const ob = document.getElementById('chara2com');
    ob.style.visibility = 'hidden';
  }
  if (visifuki3 == 'visible') {
    visifuki3 = 'hidden';
    const ob = document.getElementById('chara3com');
    ob.style.visibility = 'hidden';
  }
}

const elementfather1 = document.getElementById('chara1');
const elementfather2 = document.getElementById('chara2');
const elementfather3 = document.getElementById('chara3');

const HTML = document.querySelector('html');

elementfather1.addEventListener('animationiteration', () => {
  const animationCount1 = Number(getComputedStyle(HTML).getPropertyValue('--animationCount1'));
  HTML.style.setProperty('--animationCount1', animationCount1 + 1);
});

elementfather2.addEventListener('animationiteration', () => {
  const animationCount2 = Number(getComputedStyle(HTML).getPropertyValue('--animationCount2'));
  HTML.style.setProperty('--animationCount2', animationCount2 + 1);
});

elementfather3.addEventListener('animationiteration', () => {
  const animationCount3 = Number(getComputedStyle(HTML).getPropertyValue('--animationCount3'));
  HTML.style.setProperty('--animationCount3', animationCount3 + 1);
});


function stop(chara) {
  const ele = document.getElementById(chara);


  if (chara == 'chara1') {
    settingTime(chara, motion1);
    ele.classList.remove(...ele.classList);
    ele.classList.add("is-stop1");
  }
  if (chara == 'chara2') {
    settingTime(chara, motion2);
    ele.classList.remove(...ele.classList);
    ele.classList.add("is-stop2");
  }
  if (chara == 'chara3') {
    settingTime(chara, motion3);
    ele.classList.remove(...ele.classList);
    ele.classList.add("is-stop3");
  }
  ele.addEventListener("animationend", (event) => {
    /* イベント対象のアニメーション名で処理の出し分可能 */
    if (ele.classList == "bunshin1" || ele.classList == "bunshin2" || ele.classList == "bunshin3") {
      onHidden(chara + "clone");
    }
    ele.classList.remove(...ele.classList);

    if (chara == 'chara1') {
      HTML.style.setProperty("--animationCount1", 1);
      motion1 = 'null';
      ele.classList.remove("is-stop1");
    }
    if (chara == 'chara2') {
      HTML.style.setProperty("--animationCount2", 1);
      motion2 = 'null';
      ele.classList.remove("is-stop2");
    }
    if (chara == 'chara3') {
      HTML.style.setProperty("--animationCount3", 1);
      motion3 = 'null';
      ele.classList.remove("is-stop3");
    }
  });
}

function settingTime(chara, motion) {
  document.getElementById(chara).style.setProperty('--name', motion);
  if (motion == 'purun') {
    document.getElementById(chara).style.setProperty('--second', '2s');
  }
  if (motion == 'korokoro') {
    document.getElementById(chara).style.setProperty('--second', '2.5s');
  }
  if (motion == 'pyon') {
    document.getElementById(chara).style.setProperty('--second', '2.5s');
  }
  if (motion == 'poyoon') {
    document.getElementById(chara).style.setProperty('--second', '2s');
  }
  if (motion == 'purupurun') {
    document.getElementById(chara).style.setProperty('--second', '2s');
  }
  if (motion == 'pururun') {
    document.getElementById(chara).style.setProperty('--second', '1.6s');
  }
  if (motion == 'puyon') {
    document.getElementById(chara).style.setProperty('--second', '1.3s');
  }
  if (motion == 'papa') {
    document.getElementById(chara).style.setProperty('--second', '4s');
  }
  if (motion == 'flow') {
    document.getElementById(chara).style.setProperty('--second', '10s');
  }
  if (motion == 'jetto') {
    document.getElementById(chara).style.setProperty('--second', '10s');
  }
  if (motion == 'look1' || motion == 'look2' || motion == 'look3') {
    document.getElementById(chara).style.setProperty('--second', '10s');
  }
  if (motion == 'kurukuru') {
    document.getElementById(chara).style.setProperty('--second', '3s');
  }
  if (motion == 'bunshin1' || motion == 'bunshin2' || motion == 'bunshin3') {
    document.getElementById(chara).style.setProperty('--second', '10s');
  }
}


function waitMove(chara) {
  if (chara == 'chara1') {
    if (mode1 > 0) {
      clearInterval(mode1);
    }
  }
  if (chara == 'chara2') {
    if (mode2 > 0) {
      clearInterval(mode2);
    }
  }
  if (chara == 'chara3') {
    if (mode3 > 0) {
      clearInterval(mode3);
    }
  }

  let num = Math.floor(Math.random() * Object.keys(motionWait).length) + 1;
  motionWait[num](chara);
  // purun(chara);
}


function clickMove(chara) {
  activeMode = 0;
  allOffClick();
  console.log(chara + "クリック処理開始");
  if (chara == 'chara1') {
    if (mode1 > 0) {
      clearInterval(mode1);
    }
  }
  if (chara == 'chara2') {
    if (mode2 > 0) {
      clearInterval(mode2);
    }
  }
  if (chara == 'chara3') {
    if (mode3 > 0) {
      clearInterval(mode3);
    }
  }
  // let num = Math.floor(Math.random() * Object.keys(motionClick).length) + 1;
  motionClick[count](chara);
  count = count + 1;
  if (count == 5) {
    count = 1;
  }
}