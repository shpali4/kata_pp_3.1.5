// SHOW HEADER, TABLE PRINCIPAL, TABLE USERS
$(async function () {
    await getHeaderPrincipal();
    await getSingleUserTable();
    await getUserTable();
})
async function getData(url) {
    const response = await fetch(url);
    return await response.json();
}
async function getHeaderPrincipal() {
    const principalResponse = getData(`http://localhost:8080/api/principal`);
    principalResponse.then(data => {
        document.getElementById("headerPrincipalUsername").innerHTML = data.email;
        document.getElementById("headerPrincipalRoles").innerHTML =
            data.roles.map(role => " " + role.name.replace('ROLE_', ''));
    })
}
async function getSingleUserTable() {
    const allUsersResponse = getData(`http://localhost:8080/api/principal`);
    $('#singleUserTable').empty();
    allUsersResponse.then(user => {
        let userRow = `$(
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.roles.map(role => " " + role.name.replace('ROLE_', ''))}</td>
            </tr>
        )`;
        $('#singleUserTable').append(userRow);
    })
}
async function getUserTable() {
    const allUsersResponse = getData(`http://localhost:8080/api/allUsers`);
    $('#allUsersTable').empty();
    allUsersResponse.then(data => {
        data.forEach(user => {
            let userRow = `$(
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.roles.map(role => " " + role.name.replace('ROLE_', ''))}</td>
                    <td>
                        <button type="button" class="btn btn-info btn-sm text-white" data-bs-toggle="modal" 
                            id="buttonEdit" data-action="edit" data-id="${user.id}" data-bs-target="#editUserModal"
                            onclick="getEditModalData(${user.id})">Edit
                        </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" id="buttonDelete"
                            data-action="delete" data-id="${user.id}" data-bs-target="#deleteUserModal"
                            onclick="getDeleteModalData(${user.id})">Delete
                        </button>
                    </td>
                </tr>)`;
            $('#allUsersTable').append(userRow);
        })
    })
}
// SHOW HEADER, TABLE PRINCIPAL, TABLE USERS
// EDIT MODAL, EDIT
async function getEditModalData(id) {
    await getData(`http://localhost:8080/api/user/` + id).then(async user => {
        let formEdit = document.forms["formEditUser"];
        formEdit.id.value = user.id;
        formEdit.name.value = user.name;
        formEdit.surname.value = user.surname;
        formEdit.age.value = user.age;
        formEdit.email.value = user.email;
        $('#rolesEdit').empty();
        let i = 0;
        await getData("/api/allRoles").then((data) => {
            data.forEach(role => {
                formEdit.selectRoles.options[i] = new Option();
                formEdit.selectRoles.options[i].text = role.name.replace('ROLE_', '');
                formEdit.selectRoles.options[i].value = role.id;
                i++;
            })
        });
    })
}
async function editUser() {
    document.forms["formEditUser"].addEventListener("submit", ev => {
        ev.preventDefault();
    })

    let formEdit = document.forms["formEditUser"];
    let options = formEdit.selectRoles.options;
    let roles = [];
    for (let i in options) {
        if (options[i].selected) {
            roles.push({
                id: options[i].value,
                name: options[i].text
            })
        }
    }
    let user = {
        id: formEdit.id.value,
        name: formEdit.name.value,
        surname: formEdit.surname.value,
        age: formEdit.age.value,
        email: formEdit.email.value,
        password: formEdit.password.value,
        roles: roles
    }
    await fetch("http://localhost:8080/api/user/" + document.forms["formEditUser"].id.value, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    });
    await getUserTable();
}
// EDIT MODAL, EDIT
// DELETE MODAL, DELETE
async function getDeleteModalData(id) {
    await getData(`http://localhost:8080/api/user/` + id).then(user => {

        let formDelete = document.forms["formDeleteUser"];

        formDelete.id.value = user.id;
        formDelete.name.value = user.name;
        formDelete.surname.value = user.surname;
        formDelete.age.value = user.age;
        formDelete.email.value = user.email;
        $('#rolesDelete').empty();
        let i = 0;
        user.roles.map(role => {
                formDelete.selectRoles.options[i++] =
                    new Option(role.name.replace('ROLE_', ''), `${role.id}`);
            }
        );
    })
}
async function deleteUser() {
    document.forms["formDeleteUser"].addEventListener("submit", ev => {
        ev.preventDefault();
        $('#deleteFormCloseButton').click();
    })
    await fetch("http://localhost:8080/api/user/" + document.forms["formDeleteUser"].id.value, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    await getUserTable();
}
// DELETE MODAL, DELETE
// NEW USER
async function getNewUserRolesField() {
    $('#rolesInput').empty();
    let i = 0;
    await getData("/api/allRoles").then((data) => {
        data.forEach(role => {
            document.forms["formNewUser"].selectRoles.options[i] = new Option();
            document.forms["formNewUser"].selectRoles.options[i].text = role.name;
            document.forms["formNewUser"].selectRoles.options[i].value = role.id;
            i++;
        })
    });
}

document.forms["formNewUser"].addEventListener('submit', addNewUser);

async function addNewUser(event) {
    event.preventDefault();

    let formAddNew = document.forms["formNewUser"];
    let options = formAddNew.selectRoles.options;
    let roles = [];
    for (let i in options) {
        if (options[i].selected) {
            roles.push({
                id: options[i].value,
                name: options[i].text
            })
        }
    }
    let user = {
        name: formAddNew.firstNameInput.value,
        surname: formAddNew.lastNameInput.value,
        age: formAddNew.ageInput.value,
        email: formAddNew.emailInput.value,
        password: formAddNew.passwordInput.value,
        roles: roles
    }
    console.log(user);
    await fetch("http://localhost:8080/api/newUser", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    });
    await getUserTable();
    $('#userTable-tab').click();
    formAddNew.reset();
}
// NEW USER