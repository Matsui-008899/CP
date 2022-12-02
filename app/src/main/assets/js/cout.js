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
function korokoro() {
  document.getElementById("mitarashi").classList.remove(motion);
  looopAnimation("mitarashi", "korokoro");
  motion = "korokoro";
}

function pyon() {
  document.getElementById("mitarashi").classList.remove(motion);
  looopAnimation("mitarashi", "pyon");
  motion = "pyon";
}

function poyoon() {
  document.getElementById("mitarashi").classList.remove(motion);
  looopAnimation("mitarashi", "poyoon");
  motion = "poyoon";
}

function purupurun() {
  document.getElementById("mitarashi").classList.remove(motion);
  looopAnimation("mitarashi", "purupurun");
  motion = "purupurun";
}

function pururun() {
  document.getElementById("mitarashi").classList.remove(motion);
  looopAnimation("mitarashi", "pururun");
  motion = "pururun";
}

function puyon() {
  document.getElementById("mitarashi").classList.remove(motion);
  looopAnimation("mitarashi", "puyon");
  motion = "puyon";
}
