const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

const joinBtn = document.querySelector("#join-btn");
const leaveBtn = document.querySelector("#leave-btn");

let queryParams = {
	method: "POST",
	credentials: 'include',
	headers: {
    	[csrfHeader]: csrfToken,
    	'Content-Type': 'application/json'
	}
};

joinBtn.addEventListener("click", () => {
	fetch(`/publisher/groups/${groupId}/join`, queryParams)
	.then(response => response.json())
	.then(obj => {
		console.log(obj);
		if (obj.status == "ok") {
			joinBtn.style.display = "none";
			leaveBtn.style.display = "block";
		}
	});
});

leaveBtn.addEventListener("click", () => {
	fetch(`/publisher/groups/${groupId}/exit`, queryParams)
	.then(response => response.json())
	.then(obj => {
		console.log(obj);
		if (obj.status == "ok") {
			joinBtn.style.display = "block";
			leaveBtn.style.display = "none";
		}
	});
});