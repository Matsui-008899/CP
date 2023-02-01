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

var motionWait = {
  1: function (chara) { purun(chara); },
  2: function (chara) { pyon(chara); },
  3: function (chara) { poyoon(chara); },
  4: function (chara) { purupurun(chara); },
  5: function (chara) { pururun(chara); },
  6: function (chara) { puyon(chara); },
  7: function (chara) { papa(chara); },
  8: function (chara) { flow(chara); },
};

var motionClick = {
  1: function (chara) { korokoro(chara); },
  2: function (chara) { jetto(chara); },
  3: function (chara) { look(chara); },
};
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
  setTimeout(waitMove, 5000, chara);
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
    anim(chara, 'motion1', "look", motion1);
  } else if (chara == 'chara2') {
    anim(chara, 'motion2', "look", motion2);
  } else if (chara == 'chara3') {
    anim(chara, 'motion3', "look", motion3);
  }
}

function anim(chara, motion, motionName, motionNull) {
  if (motionNull != 'null') {
    stop(chara);

    console.log(chara + 'アニメーション処理待ち');
    document.getElementById(chara).addEventListener('animationend', () => {
      document.getElementById(chara).classList.remove(motion);
      looopAnimation(chara, motionName);
      if (motion == 'motion1') {
        motion1 = motionName;
      }
      if (motion == 'motion2') {
        motion2 = motionName;
      }
      if (motion == 'motion3') {
        motion3 = motionName;
      }

    });
  } else {
    console.log('事前アニメーションなし');
    document.getElementById(chara).classList.remove(motion);
    looopAnimation(chara, motionName);
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
}

function tako() {
  document.getElementById("chara1").src = "images/tako.png";
}

function neko() {
  document.getElementById("chara2").src = "images/neko.png";
}
function neko2() {
  document.getElementById("chara2").src = "images/neko2.png";
}
function neko3() {
  document.getElementById("chara2").src = "images/neko3.png";
}
function u_pa_() {
  document.getElementById("chara3").src = "images/u_pa_.png";
}
function u_pa_2() {
  document.getElementById("chara3").src = "images/u_pa_2.png";
}

function hugeBack() {
  var ele = document.getElementById("back");
  ele.style.cssText = 'width: 100%;   height: 100%;   background: url("images/bg_house_living.jpg") no-repeat center/80%;   background-size: 400px 200px;';
  if (visiChara1 == "visible") {
    var chara1 = document.getElementById("chara1");
    chara1.style.cssText = 'visibility: visible; z-index: 30;  top: 120px;  bottom: 0;';
  }
  if (visiChara2 == "visible") {
    var chara2 = document.getElementById("chara2");
    chara2.style.cssText = 'visibility: visible; z-index: 20;  right: 50%;  top: 100px;';
  }
  if (visiChara3 == "visible") {
    var chara3 = document.getElementById("chara3");
    chara3.style.cssText = 'visibility: visible; z-index: 10;  right: -50%;  top: 100px;';
  }

}

function smallBack() {
  var ele = document.getElementById("back");
  ele.style.cssText = 'width: 100%; height: 100px; background: url("images/bg_natural_sougen.jpg") no-repeat center/80%; background-size: 500px 100px;';

  if (visiChara1 == "visible") {
    var chara1 = document.getElementById("chara1");
    chara1.style.cssText = 'visibility: visible; z-index: 30;  top: 30px;';
  }
  if (visiChara2 == "visible") {
    var chara2 = document.getElementById("chara2");
    chara2.style.cssText = 'visibility: visible; z-index: 20;  right: 50%;  top: 30px;';

  }
  if (visiChara3 == "visible") {
    var chara3 = document.getElementById("chara3");
    chara3.style.cssText = 'visibility: visible; z-index: 10;  right: -50%;  top: 30px;';
  }
}


function huhouShinnyuu() {
  var ele = document.getElementById("back");
  ele.style.cssText = 'width: 100%; height: 100%; background: url("images/_214558.jpg") no-repeat center/80%; background-size: 450px 100px;';

  if (visiChara1 == "visible") {
    var chara1 = document.getElementById("chara1");
    chara1.style.cssText = 'visibility: visible; z-index: 30;  top: 30px;';
  }
  if (visiChara2 == "visible") {
    var chara2 = document.getElementById("chara2");
    chara2.style.cssText = 'visibility: visible; z-index: 20;  right: 50%;  top: 30px;';

  }
  if (visiChara3 == "visible") {
    var chara3 = document.getElementById("chara3");
    chara3.style.cssText = 'visibility: visible; z-index: 10;  right: -50%;  top: 30px;';
  }
}

function ryokan() {
  var ele = document.getElementById("back");
  ele.style.cssText = 'width: 100%; height: 100%; background: url("images/bg_ryokan_hiroen.jpg") no-repeat center/80%; background-size: 450px 100px;';

  if (visiChara1 == "visible") {
    var chara1 = document.getElementById("chara1");
    chara1.style.cssText = 'visibility: visible; z-index: 30;  top: 30px;';
  }
  if (visiChara2 == "visible") {
    var chara2 = document.getElementById("chara2");
    chara2.style.cssText = 'visibility: visible; z-index: 20;  right: 50%;  top: 15px;';

  }
  if (visiChara3 == "visible") {
    var chara3 = document.getElementById("chara3");
    chara3.style.cssText = 'visibility: visible; z-index: 10;  right: -50%;  top: 15px;';
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
  HTML.style.setProperty('--animationCount2', animationCount3 + 1);
});


function stop(chara) {
  console.log(chara + '停止処理開始');
  const ele = document.getElementById(chara);
  if (chara == 'chara1') {
    console.log(chara + "の" + motion1 + 'モーション呼び出し' + ele.classList);
    settingTime(chara, motion1);
    ele.classList.remove(motion1);
    ele.classList.add("is-stop1");
  }
  if (chara == 'chara2') {
    console.log(chara + "の" + motion2 + 'モーション呼び出し' + ele.classList);
    settingTime(chara, motion2);
    ele.classList.remove(motion2);
    ele.classList.add("is-stop2");
  }
  if (chara == 'chara3') {
    console.log(chara + "の" + motion3 + 'モーション呼び出し' + ele.classList);
    settingTime(chara, motion3);
    ele.classList.remove(motion3);
    ele.classList.add("is-stop3");
  }
  console.log(chara + '停止待ち');
  ele.addEventListener("animationend", (event) => {
    /* イベント対象のアニメーション名で処理の出し分可能 */
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
    console.log(chara + '停止処理完了');

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
  if (motion == 'look') {
    document.getElementById(chara).style.setProperty('--second', '10s');
  }
  if (motion == 'kurukuru') {
    document.getElementById(chara).style.setProperty('--second', '3s');
  }
}

let mode1 = 0;
let mode2 = 0;
let mode3 = 0;

function waitMove(chara) {
  console.log(chara + "待機処理開始");
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

  let run = Math.floor(Math.random() * 10000 + 5000);
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


function clickMove(chara) {
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

  let num = Math.floor(Math.random() * Object.keys(motionClick).length) + 1;
  motionClick[num](chara);
  console.log(chara + "クリック処理開始");

  //待機処理
  if (chara == 'chara1') {
    mode1 = setInterval(waitMove, 10000, chara);
  }
  if (chara == 'chara2') {
    mode2 = setInterval(waitMove, 10000, chara);
  }
  if (chara == 'chara3') {
    mode3 = setInterval(waitMove, 10000, chara);
  }
}