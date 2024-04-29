
const baseEndPoint = "http://localhost:8080/note";
const noteList = document.querySelector(".list");
const errorBox = document.querySelector(".error-box");
const errorList = document.querySelector(".error-list");
const form = document.querySelector(".form");
const formEdit = document.querySelector(".form-edit");
const formId = document.querySelector("[data-id]");
const formTitle = document.querySelector("[data-title]");
const formContent = document.querySelector("[data-content]");
const modal = document.querySelector("#editModal");
const spanCloseModal = document.querySelector(".close-span");
const btmCloseModal = document.querySelector(".cancel-btn");

async function editNote(id) {
    errorBox.style.display = "none";
    errorList.innerHTML = "";

    const url = `${baseEndPoint}/edit?id=${id}`;
    const params = {
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json; charset=UTF-8'
        })
    }
    let response = await fetch(url, params);

    if (response.ok) {
        const note = await response.json()
        formId.value = note.id;
        formTitle.value = note.title;
        formContent.value = note.content;
        modal.style.display = "block";
    } else {
        errorBox.style.display = "block";
        buildErrorList(await response.json());
    }
}


const onFormEditSubmit = (e) => {
    e.preventDefault();

    const id = formEdit.elements.id.value.trim();
    const title = formEdit.elements.title.value.trim();
    const content = formEdit.elements.content.value.trim();

    if (id && title && content) {
        e.currentTarget.reset();
        updateNote({id, title, content});
    }
}

formEdit.addEventListener("submit", onFormEditSubmit);

async function updateNote({id, title, content}) {
    console.log(id, title, content);
    errorBox.style.display = "none";
    errorList.innerHTML = "";
    const url = `${baseEndPoint}/edit`;
    const params = {
        method: 'PUT',
        body: JSON.stringify({
            id: id,
            title: title,
            content: content,
        }),
        headers: new Headers({
            'Content-Type': 'application/json; charset=UTF-8'
        })
    }
    let response = await fetch(url, params);

    if (response.ok) {
        modal.style.display = "none";
        location.href=`${baseEndPoint}`;
    } else {
        errorBox.style.display = "block";
        buildErrorList(await response.json());
    }
}


const onFormSubmit = (e) => {
    e.preventDefault();

    const title = form.elements.title.value.trim();
    const content = form.elements.content.value.trim();

    if (title && content) {
        e.currentTarget.reset();
        addNote(title, content);
    }
}

form.addEventListener("submit", onFormSubmit);

async function addNote(title, content) {
    errorBox.style.display = "none";
    errorList.innerHTML = "";
    const url = `${baseEndPoint}/create`;
    const params = {
        method: 'POST',
        body: JSON.stringify({
            title: title,
            content: content,
        }),
        headers: new Headers({
            'Content-Type': 'application/json; charset=UTF-8'
        })
    }
        let response = await fetch(url, params);

        if (response.ok) {
            buildNotesList([await response.json()]);
        } else {
            errorBox.style.display = "block";
            buildErrorList(await response.json());
        }
}

async function deleteNote(id) {
    const url = `${baseEndPoint}/delete`;
    const params = {
        method: 'DELETE',
        body: JSON.stringify({id: id}),
        headers: new Headers({
            'Content-Type': 'application/json; charset=UTF-8'
        })
    }
    let response = await fetch(url, params);

    if (response.ok) {
        location.href=`${baseEndPoint}`
    } else {
        buildErrorList(await response.json());
    }
}

const buildNotesList = (notes) => {
    noteList.insertAdjacentHTML("beforeend", notes.map(fillNotesList).join(""));
}

const fillNotesList = ({id, title, content}) => {
    return `
    <li class="list-item">
        <p class="item-id">${id}</p>
        <p class="item-title">${title}</p>
        <p class="item-content">${content}</p>
        <button class="btn" type="button" onclick="editNote('${id}')">Edit</button>
        <button class="btn" type="button" onclick="deleteNote('${id}')">Delete</button>
    </li>`;
};

async function getNotes() {
    const url = `${baseEndPoint}/list`;
    const params = {
        method: "GET",
        headers: {"Content-Type": "application/json; charset=UTF-8",},
    }
    let response = await fetch(url, params);

    if (response.ok) {
        buildNotesList(await response.json());
    } else {
        buildErrorList(await response.json());
    }
}

const buildErrorList = ({errors}) => {

    if (Array.isArray(errors)) {
        errorList.insertAdjacentHTML("beforeend", errors.map(error => {
            return `<p>${key}</p><li>${error}</li>`;
        }).join(""));
    } else {
        const keys = Object.keys(errors);
        keys.forEach(key => {
            errorList.insertAdjacentHTML("beforeend", errors[key].map(error => {
                return `<p>${key}</p><li>${error}</li>`;
            }).join(""));
        })
    }
}

spanCloseModal.onclick = function() {
    modal.style.display = "none";
}
btmCloseModal.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}


getNotes().then();