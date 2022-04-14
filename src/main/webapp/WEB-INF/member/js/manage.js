function onSaveClick(id) {
    const username = document.getElementById(`username${id}`).innerText;
    const password = document.getElementById(`password${id}`).innerText;
    const nickname = document.getElementById(`nickname${id}`).innerText;
    const pass = !!+document.getElementById(`pass${id}`).value;
    const roleId = document.getElementById(`roleId${id}`).value;
    const updater = sessionStorage.getItem('nickname');

    fetch('save', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            id,
            username,
            password,
            nickname,
            pass,
            roleId,
            updater
        })
    })
        .then(resp => resp.json())
        .then(body => {
            const { successful, message } = body;
            if (successful) {
                alert('存檔成功!');
                location.reload();
            } else {
                alert(message | '存檔失敗');
            }
        });
}

function onRemoveClick(id) {
    if (!confirm('確定刪除?')) {
        return;
    }
    fetch('remove', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ id })
    })
        .then(resp => resp.json())
        .then(body => {
            if (body.successful) {
                location.reload();
            }
        });
}