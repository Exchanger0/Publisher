const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

let like = document.querySelector(".like");
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
like.addEventListener("click", postLike);

let dislike = document.querySelector(".dislike");
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
dislike.addEventListener("click", postDislike);