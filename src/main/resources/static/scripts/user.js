const elements = [[".show-posts", ".post-list"], [".show-groups", ".group-list"], [".show-created-groups", ".created-group-list"]];

for (let el of elements) {
    let btn = document.querySelector(el[0])
    let list = document.querySelector(el[1])

    btn.addEventListener("click", () => {
      if (list.style.display === "none" || list.style.display === "") {
        list.style.display = "block";
        btn.textContent = "Hide";
      } else {
        list.style.display = "none";
        btn.textContent = "Show";
      }
    });
}