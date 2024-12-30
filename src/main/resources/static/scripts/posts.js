const container = document.querySelector(".posts")
let next = sessionStorage.getItem("next");
if (next == null) {
	next = 1;
}else {
	next = parseInt(next);
}
console.log(next);

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

//получает страницу постов и создает для каждого html представлеение
async function get(start) {
	let response = await fetch("/publisher/posts/data?start=" + start);
	let posts = await response.json();

	for(let post of posts) {
		container.appendChild(createPostHtml(post))
	}

	console.log("end start=" + start);
}

//загружает все посты с предыдущей сессии
async function load() {
	for(let i = 0; i < next; i++) {
		console.log("get page with index=" + i);
		await get(i);
	}
	//установление предыдущего скролла
	const scrollPosition = sessionStorage.getItem("scrollPosition");
	if (scrollPosition) {
	    window.scrollTo(0, parseFloat(scrollPosition));
	}
}

load();

//получение следующей страницы постов
document.querySelector("#load-btn").addEventListener("click", () => {
	console.log("get page with index=" + next);
	get(next);
	next += 1;
});

//сохранение текущего положения скоролла и индекса следующей страницы
window.addEventListener("beforeunload", function () {
    sessionStorage.setItem("scrollPosition", window.scrollY);
    sessionStorage.setItem("next", next);
});