let motion1 = null;
let motion2 = null;
let motion3 = null;
let visiChara2 = 'hidden';
let visiChara3 = 'hidden';
let visifuki1 = 'hidden';
let visifuki2 = 'hidden';
let visifuki3 = 'hidden';

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
    anim(chara, motion1, "purun");
  } else if (chara == 'chara2') {
    anim(chara, motion1, "purun");
  } else if (chara == 'chara3') {
    anim(chara, motion1, "purun");
  }

}

/**
 * 予定追加画面
 */
function korokoro(chara) {
  if (chara == 'chara1') {
    anim(chara, motion1, "korokoro");
  } else if (chara == 'chara2') {
    anim(chara, motion2, "korokoro");
  } else if (chara == 'chara3') {
    anim(chara, motion3, "korokoro");
  }

}

function pyon(chara) {
  if (chara == 'chara1') {
    anim(chara, motion1, "pyon");
  } else if (chara == 'chara2') {
    anim(chara, motion2, "pyon");
  } else if (chara == 'chara3') {
    anim(chara, motion3, "pyon");
  }

}

function poyoon(chara) {
  if (chara == 'chara1') {
    anim(chara, motion1, "poyoon");
  } else if (chara == 'chara2') {
    anim(chara, motion2, "poyoon");
  } else if (chara == 'chara3') {
    anim(chara, motion3, "poyoon");
  }

}

function purupurun(chara) {
  if (chara == 'chara1') {
    anim(chara, motion1, "purupurun");
  } else if (chara == 'chara2') {
    anim(chara, motion2, "purupurun");
  } else if (chara == 'chara3') {
    anim(chara, motion3, "purupurun");
  }

}

function pururun(chara) {
  if (chara == 'chara1') {
    anim(chara, motion1, "pururun");
  } else if (chara == 'chara2') {
    anim(chara, motion2, "pururun");
  } else if (chara == 'chara3') {
    anim(chara, motion3, "pururun");
  }

}

function puyon(chara) {
  if (chara == 'chara1') {
    anim(chara, motion1, 'puyon');
  } else if (chara == 'chara2') {
    anim(chara, motion2, 'puyon');
  } else if (chara == 'chara3') {
    anim(chara, motion3, 'puyon');
  }

}


function papa(chara) {
  if (chara == 'chara1') {
    anim(chara, motion1, 'papa');
  } else if (chara == 'chara2') {
    anim(chara, motion2, 'papa');
  } else if (chara == 'chara3') {
    anim(chara, motion3, 'papa');
  }
}

function kurukuru(chara) {
  if (chara == 'chara1') {
    anim(chara, motion1, 'kurukuru');
  } else if (chara == 'chara2') {
    anim(chara, motion2, 'kurukuru');
  } else if (chara == 'chara3') {
    anim(chara, motion3, 'kurukuru');
  }
  window.setTimeout(purun, 5000, chara);
}

function anim(chara, motion, motionName) {
  document.getElementById(chara).classList.remove(motion);
  looopAnimation(chara, motionName);
  motion = motionName;
}

//挿入準備
function setVisible(chara) {
  const ob = document.getElementById(chara);
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
  var chara1 = document.getElementById("chara1");
  chara1.style.cssText = 'z-index: 30;  top: 120px;  bottom: 0;';
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
  var chara1 = document.getElementById("chara1");
  chara1.style.cssText = 'z-index: 30;  top: 30px;';
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
  ele.style.cssText = 'width: 100%; height: 100%; background: url("images/_214558.jpg") no-repeat center/80%; background-size: 370px 100px;';
  var chara1 = document.getElementById("chara1");
  chara1.style.cssText = 'z-index: 30;  top: 30px;';
  if (visiChara2 == "visible") {
    var chara2 = document.getElementById("chara2");
    chara2.style.cssText = 'visibility: visible; z-index: 20;  right: 50%;  top: 30px;';

  }
  if (visiChara3 == "visible") {
    var chara3 = document.getElementById("chara3");
    chara3.style.cssText = 'visibility: visible; z-index: 10;  right: -50%;  top: 30px;';
  }
}

function visibleBalloon(name, level,chara) {
  const putName = document.getElementById(name + 'name');
  const putLevel = document.getElementById(name + 'level');
  console.log(name+"com");
  const ob = document.getElementById(name+'com');
  ob.style.visibility='visible';
  if (name == 'chara1') {
    visifuki1 = 'visible';
  }
  if (name == 'chara2') {
    visifuki2 = 'visible';
  }
  if (name == 'chara3') {
    visifuki3 = 'visible';
  }


  console.log('名前：' + chara);
  console.log('現在レベル：' + level);
  putName.textContent = '名前：' + chara;
  putLevel.textContent = '現在レベル：' + level;

}

function invisibleBalloon(){
  if (visifuki1 == 'visible') {
    visifuki1 = 'hidden';
    const ob = document.getElementById('chara1com');
    ob.style.visibility='hidden';
  
  }
  if (visifuki2 == 'visible') {
    visifuki2 = 'hidden';
    const ob = document.getElementById('chara2com');
    ob.style.visibility='hidden';
  }
  if (visifuki3 == 'visible') {
    visifuki3 = 'hidden';
    const ob = document.getElementById('chara3com');
    ob.style.visibility='hidden';
  }
}