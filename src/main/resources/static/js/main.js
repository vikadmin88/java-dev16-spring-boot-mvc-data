
const editNote = (id) => location.href=`http://localhost:8080/note/edit?id=${id}`;

const deleteNote = (id) => {
    const url = 'http://localhost:8080/note/delete';
    postDelete(url, id).then(() => location.href=`http://localhost:8080/note/list`);
}

async function postDelete(url = "", id) {
    return await fetch(url, {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",},
        redirect: "follow",
        body: "id=" + id,
    });
}
