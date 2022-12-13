let motion;
//背景の挙動設定
document.querySelector(`.container`).animate(
  [
    { backgroundPosition: '0 0' },
    { backgroundPosition: '-100% 0' }
  ],
  {
    duration: 5000,
    iterations: Infinity
  }
);


// CSSアニメーションを間隔を空けてループ再生させる処理
function looopAnimation(id, className ) {
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
function purun() {
  document.getElementById("mitarashi").classList.remove(motion);
  looopAnimation("mitarashi", "purun");
  motion = "purun";
}

/**
 * 予定追加画面
 */
function korokoro(chara) {
  document.getElementById(chara).classList.remove(motion);
  looopAnimation(chara, "korokoro");
  motion = "korokoro";
}

function pyon(chara) {
  document.getElementById(chara).classList.remove(motion);
  looopAnimation(chara, "pyon");
  motion = "pyon";
}

function poyoon(chara) {
  document.getElementById(chara).classList.remove(motion);
  looopAnimation(chara, "poyoon");
  motion = "poyoon";
}

function purupurun(chara) {
  document.getElementById(chara).classList.remove(motion);
  looopAnimation(chara, "purupurun");
  motion = "purupurun";
}

function pururun(chara) {
  document.getElementById(chara).classList.remove(motion);
  looopAnimation(chara, "pururun");
  motion = "pururun";
}

function puyon(chara) {
  document.getElementById(chara).classList.remove(motion);
  looopAnimation(chara, "puyon");
  motion = "puyon";
}


function papa(chara) {
  document.getElementById(chara).classList.remove(motion);
  looopAnimation(chara, "papa");
  motion = "papa";
}

//御手洗挿入
function mitarashi() {
  document.getElementById("chara1").src = "images/mitarashi.png";
}