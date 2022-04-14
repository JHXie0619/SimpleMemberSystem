(() => {
  document.getElementById('btn1').onclick = () => {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    fetch('login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password }),
    })
      .then((resp) => resp.json())
      .then((body) => {
        const errMsg = document.getElementById('errMsg');
        errMsg.innerHTML = '';
        const { successful, message } = body;
        if (successful) {
          const { id, nickname, roleId } = body;
          sessionStorage.setItem('id', id);
          sessionStorage.setItem('nickname', nickname);
          sessionStorage.setItem('roleId', roleId);
          location = '../index.html';
        } else {
          errMsg.innerHTML = message;
        }
      });
  };
})();
