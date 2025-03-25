const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

async function postLike() {
	let response = await fetch(window.location.pathname + "/like", {
		method: "POST",
		credentials: 'include',
		headers: {
        	[csrfHeader]: csrfToken,
        	'Content-Type': 'application/json'
    	}
	});
	
	if (response.redirected) {
		window.location.href = response.url;	
	} else {
		let data = await response.json();
		if (data.success) {
			like.classList.add("active");
		}else {
			like.classList.remove("active");
		}
		like.textContent = data.likes + " likes";
	}
}
let like = document.querySelector(".like");
like.addEventListener("click", postLike);

async function postDislike() {
	let response = await fetch(window.location.pathname + "/dislike", {
		method: "POST",
		credentials: 'include',
		headers: {
        	[csrfHeader]: csrfToken,
        	'Content-Type': 'application/json'
    	}
	});

	if (response.redirected) {
		window.location.href = response.url;	
	} else {
		let data = await response.json();
		if (data.success) {
			dislike.classList.add("active");
		}else {
			dislike.classList.remove("active");
		}
		dislike.textContent = data.dislikes + " dislikes";
	}	
}
let dislike = document.querySelector(".dislike");
dislike.addEventListener("click", postDislike);

let commentContainer = document.querySelector(".comments");
let commentsMap = new Map();

async function getCreateCommentsForm(parentId) {
	let postId = window.location.pathname.slice(window.location.pathname.lastIndexOf('/') + 1);
	window.location.href = "/publisher/comments/create?postId=" + postId + "&parentId=" + parentId;
}

function createAndAddCommentHtml(commentObj) {
	let comment = document.createElement("div");
	comment.classList.add("comment");

	let authorUsername = document.createElement("p");
	authorUsername.textContent = commentObj.author.username;
	let content = document.createElement("p");
	content.textContent = commentObj.content;
	let childContainer = document.createElement("div");
	childContainer.classList.add("child-container");
	childContainer.style.marginLeft = "30px";
	let loadChildComments = document.createElement("p");
	loadChildComments.textContent = "load";
	loadChildComments.classList.add("text-button");
	loadChildComments.addEventListener("click", () => {
		let promise = loadComments("parentid", commentObj.id, commentObj.next);
		promise.then(res => {
			if (res) {
				commentObj.next++;
			}
		});
	});
	let doComment = document.createElement("p");
	doComment.textContent = "comment";
	doComment.classList.add("text-button");
	doComment.addEventListener("click", () => getCreateCommentsForm(commentObj.id));

	comment.appendChild(authorUsername);
	comment.appendChild(content);
	comment.appendChild(childContainer);
	comment.appendChild(doComment);
	comment.appendChild(loadChildComments);

	commentsMap.set(commentObj.id, comment);

	if (commentObj.parent !== null) {
		if (commentsMap.has(commentObj.parent.id)) {
			let parent = commentsMap.get(commentObj.parent.id);
			parent.querySelector(".child-container").appendChild(comment);
		}
	}else {
		commentContainer.appendChild(comment);
	}
	
}

let globalNext = 0;
async function loadComments(field, id, start) {
	let response = await fetch("/publisher/comments?start=" + start + "&field=" + field + "&id=" + id);
	let comments = await response.json();
	console.log(comments);
	for (let commentObj of comments) {
		commentObj.next = 0;
		createAndAddCommentHtml(commentObj);
	}
	return comments.length > 0;
}
let loadCommentsBtn = document.querySelector("#load-comments");
loadCommentsBtn.addEventListener("click", () => {
	let promise = loadComments("postid", window.location.pathname.slice(window.location.pathname.lastIndexOf('/') + 1), globalNext);
	promise.then(res => {
		if (res) {
			globalNext++;
		}
	});
});
