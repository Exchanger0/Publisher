let isSearchReq = sessionStorage.getItem("isSearchReq");
if (isSearchReq == null) {
	isSearchReq = false;
}else {
	isSearchReq = (isSearchReq.toLowerCase() === "true");
}
console.log(isSearchReq);

let next = sessionStorage.getItem("next");
if (next == null) {
	next = 1;
}else {
	next = parseInt(next);
}
console.log(next);

const container = document.querySelector(".posts")
let searchField = document.querySelector("#search-field");
let searchBtn = document.querySelector("#search-btn");
let cancelBtn = document.querySelector("#cancel-btn");

searchBtn.addEventListener("click", () => {
	console.log("start search");
	isSearchReq = true;
	next = 0;
	get({start: next, q: searchField.value}).then(posts => {
		container.replaceChildren();
		if (posts.length != 0) {
			addPosts(posts);
			next += 1;
		}
	});
});
cancelBtn.addEventListener("click", () => {
	isSearchReq = false;
	next = 0;
	searchField.value = "";
	get({start: next}).then(posts => {
		container.replaceChildren();
		if (posts.length != 0) {
			addPosts(posts);
			next += 1;
		}
	});
})

let q = sessionStorage.getItem("q");
if (q == null) {
	q = "";
}
searchField.value = q;


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
	for(let i = 0; i < next; i++) {
		console.log("get page with index=" + i);
		let requestParams;
		if (isSearchReq) {
			requestParams = {start: i, q: searchField.value};
		}else {
			requestParams = {start: i};
		}
		let posts = await get(requestParams);
		addPosts(posts);
	}
	//установление предыдущего скролла
	const scrollPosition = sessionStorage.getItem("scrollPosition");
	if (scrollPosition) {
	    window.scrollTo(0, parseFloat(scrollPosition));
	}
	console.log("end load");
}

//получение следующей страницы постов
document.querySelector("#load-btn").addEventListener("click", () => {
	console.log("get page with index=" + next);
	let requestParams;
	if (isSearchReq) {
		requestParams = {start: next, q: searchField.value};
	}else {
		requestParams = {start: next};
	}
	let promise = get(requestParams);
	promise.then(posts => {
		if (posts.length != 0) {
			addPosts(posts);
			next += 1;
		}	
	});
});

//сохранение текущего положения скоролла и индекса следующей страницы
window.addEventListener("beforeunload", function () {
    sessionStorage.setItem("scrollPosition", window.scrollY);
    sessionStorage.setItem("next", next);
    sessionStorage.setItem("isSearchReq", isSearchReq);
    if (isSearchReq) {
    	sessionStorage.setItem("q", searchField.value);
	}
});