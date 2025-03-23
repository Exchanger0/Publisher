let groupsData = sessionStorage.getItem("groupsData");
console.log(groupsData);
if (groupsData === null) {
	groupsData = {};
}else {
	groupsData = JSON.parse(groupsData);
}

if (groupsData.isSearchReq === undefined) {
	groupsData.isSearchReq = false;
}
console.log(groupsData.isSearchReq);
if (groupsData.next === undefined) {
	groupsData.next = 1;
}
console.log(groupsData.next);
if (groupsData.q === undefined) {
	groupsData.q = "";
}

const container = document.querySelector(".groups");
let searchField = document.querySelector("#search-field");
let searchBtn = document.querySelector("#search-btn");
let cancelBtn = document.querySelector("#cancel-btn");

searchBtn.addEventListener("click", () => {
	console.log("start search");
	groupsData.isSearchReq = true;
	groupsData.next = 0;
	get({start: groupsData.next, q: searchField.value}).then(groups => {
		container.replaceChildren();
		if (groups.length != 0) {
			addGroups(groups);
			groupsData.next += 1;
		}
	});
});
cancelBtn.addEventListener("click", () => {
	groupsData.isSearchReq = false;
	groupsData.next = 0;
	searchField.value = "";
	get({start: groupsData.next}).then(groups => {
		container.replaceChildren();
		if (groups.length != 0) {
			addGroups(groups);
			groupsData.next += 1;
		}
	});
})

searchField.value = groupsData.q;


load();

// создает html элементы для представления поста
function createGroupHtml(groupObj) {
	let group = document.createElement("div");
	group.classList.add("group");
	
	let name = document.createElement("p");
	name.classList.add("title");
	name.textContent = groupObj.name;
	name.addEventListener("click", () => {
		window.location.href = "/publisher/groups/" + groupObj.id;
	});

	let creator = document.createElement("a");
	creator.classList.add("link");
	creator.textContent = groupObj.creator.username;
	creator.href = "/publisher/users/" + groupObj.creator.id;

	let description = document.createElement("p");
	description.textContent = groupObj.description;
	description.classList.add("description");

	group.appendChild(name);
	group.appendChild(creator);
	group.appendChild(description);

	return group;
}

//получает посты
async function get(requestParams) {
	const queryString = new URLSearchParams(requestParams).toString();
	console.log("get requestParams=" + requestParams);
	let response = await fetch(`/publisher/groups/data?${queryString}`);
	let groups = await response.json();

	return groups;
}

//добавление постов на страницу
function addGroups(groups) {
	for(let group of groups) {
		container.appendChild(createGroupHtml(group))
	}
}

//загружает все посты с предыдущей сессии
async function load() {
	console.log("start load")
	for(let i = 0; i < groupsData.next; i++) {
		console.log("get page with index=" + i);
		let requestParams;
		if (groupsData.isSearchReq) {
			requestParams = {start: i, q: searchField.value};
		}else {
			requestParams = {start: i};
		}
		let groups = await get(requestParams);
		addGroups(groups);
	}
	//установление предыдущего скролла
	if (groupsData.scrollPosition !== undefined) {
	    window.scrollTo(0, groupsData.scrollPosition);
	}
	console.log("end load");
}

//получение следующей страницы постов
document.querySelector("#load-btn").addEventListener("click", () => {
	console.log("get page with index=" + groupsData.next);
	let requestParams;
	if (groupsData.isSearchReq) {
		requestParams = {start: groupsData.next, q: searchField.value};
	}else {
		requestParams = {start: groupsData.next};
	}
	let promise = get(requestParams);
	promise.then(groups => {
		if (groups.length != 0) {
			addGroups(groups);
			groupsData.next += 1;
		}	
	});
});

//сохранение текущей информации о странице
window.addEventListener("beforeunload", function () {
	console.log(groupsData);
	groupsData.scrollPosition = window.scrollY;
    if (groupsData.isSearchReq) {
    	groupsData.q = searchField.value;
	}
	sessionStorage.setItem("groupsData", JSON.stringify(groupsData));
});