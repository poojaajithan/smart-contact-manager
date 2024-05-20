let currentTheme = getTheme();
//wait for page to be loaded
document.addEventListener("DOMContentLoaded", () => {
  changeTheme();
});

//TODO:
function changeTheme() {
  //set to web page
  changePageTheme(currentTheme, '');
  //set the listener to change theme button
  const changeThemeButton = document.querySelector("#theme_change_button");

  changeThemeButton.addEventListener("click", (event) => {
    let oldTheme = currentTheme;
    console.log("Change theme button clicked");
    if (currentTheme === "dark") {
      currentTheme = "light";
    } else {
      currentTheme = "dark";
    }
    console.log(currentTheme);
    changePageTheme(currentTheme, oldTheme);
  });
}

//set theme to localstorage
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

//get theme from localstorage
function getTheme() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}

//change current page theme
function changePageTheme(newTheme, oldTheme) {
  setTheme(currentTheme);
  if (oldTheme) {
    document.querySelector("html").classList.remove(oldTheme);
  }
  document.querySelector("html").classList.add(newTheme);

  // change the text of button
  document
    .querySelector("#theme_change_button")
    .querySelector("span").textContent = newTheme == "light" ? "Dark" : "Light";
}

//change page change theme