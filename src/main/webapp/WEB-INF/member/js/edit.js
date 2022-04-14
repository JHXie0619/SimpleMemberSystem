(() => {
  init();
  document.getElementById('btn1').onclick = edit;
})();

function init() {
  fetch('getInfo', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
  })
    .then((resp) => resp.json())
    .then((body) => {
      document.getElementById("username").value = body["username"];
      document.getElementById("nickname").value = body["nickname"];
    });

  document.getElementById('oPassword').onblur = checkOldPassword;
}

function checkOldPassword() {
  const oPassword = document.getElementById('oPassword').value;
  fetch('checkPassword', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ password: oPassword })
  })
    .then(resp => resp.json())
    .then(body => {
      const btn1 = document.getElementById('btn1');
      btn1.disabled = !body['successful']
    });
}

function edit() {
  const msg = document.getElementById('msg');

  const nPassword = document.getElementById('nPassword').value;
  const confirmPassword = document.getElementById('confirmPassword').value;
  if (nPassword && confirmPassword) {
    if (nPassword.length < 6 || nPassword.length > 12) {
      msg.innerText = '密碼長度須介於6~12字元';
      return;
    }

    if (confirmPassword !== nPassword) {
      msg.innerText = '密碼與確認密碼不相符';
      return;
    }
  }

  const nickname = document.getElementById('nickname').value;
  const nicknameLength = nickname.length;
  if (nicknameLength < 1 || nicknameLength > 20) {
    msg.innerText = '暱稱長度須介於1~20字元';
    return;
  }

  msg.innerText = '';

  fetch('edit', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      password: nPassword,
      nickname
    }),
  })
    .then((resp) => resp.json())
    .then((body) => {
      const { successful, message, nickname } = body;
      if (successful) {
        msg.className = 'info';
        sessionStorage.setItem('nickname', nickname);
        document.getElementById('currentUser').innerText = nickname;
        document.getElementById('oPassword').value = '';
        document.getElementById('nPassword').value = '';
        document.getElementById('confirmPassword').value = '';
        document.getElementById('nickname').value = nickname;
        document.getElementById('btn1').disabled = true;
      } else {
        msg.className = 'error';
      }
      msg.innerText = message;
    });
}
