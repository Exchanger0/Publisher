let postsData = sessionStorage.getItem("grPostsData");
console.log(postsData);
if (postsData === null) {
	postsData = {};
}else {
	postsData = JSON.parse(postsData);
}

if (postsData.isSearchReq === undefined) {
	postsData.isSearchReq = false;
}
console.log(postsData.isSearchReq);
if (postsData.next === undefined) {
	postsData.next = 1;
}
console.log(postsData.next);
if (postsData.q === undefined) {
	postsData.q = "";
}

const container = document.querySelector(".posts");
let searchField = document.querySelector("#search-field");
let searchBtn = document.querySelector("#search-btn");
let cancelBtn = document.querySelector("#cancel-btn");
const groupId = parseInt(document.querySelector("#groupId").textContent);

searchBtn.addEventListener("click", () => {
	console.log("start search");
	postsData.isSearchReq = true;
	postsData.next = 0;
	get({start: postsData.next, title: searchField.value, tag: searchField.value, groupId: groupId})
	.then(posts => {
		container.replaceChildren();
		if (posts.length != 0) {
			addPosts(posts);
			postsData.next += 1;
		}
	});
});
cancelBtn.addEventListener("click", () => {
	postsData.isSearchReq = false;
	postsData.next = 0;
	searchField.value = "";
	get({start: postsData.next, groupId: groupId}).then(posts => {
		container.replaceChildren();
		if (posts.length != 0) {
			addPosts(posts);
			postsData.next += 1;
		}
	});
})

searchField.value = postsData.q;


load();

// создает html элементы для представления поста
function createPostHtml(postObj) {
	let post = document.createElement("div");
	post.classList.add("post");
	post.addEventListener("click", () => {
		window.location.href = "/publisher/posts/" + postObj.id;
	});

	let title = document.createElement("p");
	title.classList.add("title")
	title.textContent = postObj.title;

	let content = document.createElement("p");
	content.textContent = postObj.content;
	content.classList.add("content")

	let creationDate = document.createElement("p");
	creationDate.textContent = postObj.creationDate;
	creationDate.classList.add("creation-date")

	let likes = document.createElement("span");
	likes.textContent = postObj.likes + " likes ";
	let dislikes = document.createElement("span");
	dislikes.textContent = postObj.dislikes + " dislikes ";
	let views = document.createElement("span");
	views.textContent = postObj.views + " views ";

	let stats = document.createElement("div");
	stats.classList.add("stats")

	stats.appendChild(likes);
	stats.appendChild(dislikes);
	stats.appendChild(views);

	post.appendChild(title);
	post.appendChild(content);
	post.appendChild(creationDate);
	post.appendChild(stats);

	return post;
}

//получает посты
async function get(requestParams) {
	const queryString = new URLSearchParams(requestParams).toString();
	console.log("get requestParams=" + requestParams);
	let response = await fetch(`/publisher/posts/data?${queryString}`);
	let posts = await response.json();

	return posts;
}

//добавление постов на страницу
function addPosts(posts) {
	for(let post of posts) {
		container.appendChild(createPostHtml(post))
	}
}

//загружает все посты с предыдущей сессии
async function load() {
	console.log("start load")
	for(let i = 0; i < postsData.next; i++) {
		console.log("get page with index=" + i);
		let requestParams;
		if (postsData.isSearchReq) {
			requestParams = {start: i, title: searchField.value, tag: searchField.value, groupId: groupId};
		}else {
			requestParams = {start: i, groupId: groupId};
		}
		let posts = await get(requestParams);
		addPosts(posts);
	}
	//установление предыдущего скролла
	if (postsData.scrollPosition !== undefined) {
	    window.scrollTo(0, postsData.scrollPosition);
	}
	console.log("end load");
}

//получение следующей страницы постов
document.querySelector("#load-btn").addEventListener("click", () => {
	console.log("get page with index=" + postsData.next);
	let requestParams;
	if (postsData.isSearchReq) {
		requestParams = {start: postsData.next, title: searchField.value, tag: searchField.value, groupId: groupId};
	}else {
		requestParams = {start: postsData.next, groupId: groupId};
	}
	let promise = get(requestParams);
	promise.then(posts => {
		if (posts.length != 0) {
			addPosts(posts);
			postsData.next += 1;
		}	
	});
});

//сохранение текущей информации о странице
window.addEventListener("beforeunload", function () {
	console.log(postsData);
	postsData.scrollPosition = window.scrollY;
    if (postsData.isSearchReq) {
    	postsData.q = searchField.value;
	}
	sessionStorage.setItem("grPostsData", JSON.stringify(postsData));
});