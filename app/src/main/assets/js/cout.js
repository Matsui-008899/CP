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
function tara() {
  document.getElementById("mitarashi").classList.remove("korokoro");
  looopAnimation("mitarashi", "purun");
}

/**
 * 予定追加画面
 */
function tako() {
  document.getElementById("mitarashi").classList.remove("purun");
  looopAnimation("mitarashi", "korokoro");
}