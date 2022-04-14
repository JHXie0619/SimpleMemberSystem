(() => {
  const btn1 = document.getElementById('btn1');
  btn1.onclick = () => {
    const msg = document.getElementById('msg');

    const username = document.getElementById('username').value;
    const accLength = username.length;
    if (accLength < 5 || accLength > 50) {
      msg.innerText = '帳號長度須介於5~50字元';
      return;
    }

    const password = document.getElementById('password').value;
    const pwdLength = password.length;
    if (pwdLength < 6 || pwdLength > 12) {
      msg.innerText = '密碼長度須介於6~12字元';
      return;
    }

    const confirmPassword = document.getElementById('confirmPassword').value;
    if (confirmPassword !== password) {
      msg.innerText = '密碼與確認密碼不相符';
      return;
    }

    const nickname = document.getElementById('nickname').value;
    const nicknameLength = nickname.length;
    if (nicknameLength < 1 || nicknameLength > 20) {
      msg.innerText = '暱稱長度須介於1~20字元';
      return;
    }

    msg.innerText = '';
    fetch('register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        username,
        password,
        nickname,
      }),
    })
      .then((resp) => resp.json())
      .then((body) => {
        const { successful } = body;
        if (successful) {
          for (let input of document.getElementsByTagName('input')) {
            input.disabled = true;
          }
          btn1.disabled = true;
          msg.className = 'info';
          msg.innerText = '註冊成功';
        } else {
          msg.className = 'error';
          msg.innerText = '註冊失敗';
        }
      });
  };
})();
