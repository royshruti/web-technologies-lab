/**
 * @type {HTMLInputElement}
 */
const usernameInput = document.getElementById("username");

/**
 * @type {HTMLDivElement}
 */
const usernameValidContainer = document.getElementById("username-valid-container");
/**
 * @type {number}
 */
let validator

/**
 *
 * @param {string} message
 */
function setUsernameContainerMessage(message) {
    usernameValidContainer.innerText = message
}

/**
 * Sends username to backend and checks whether this username is available or not.
 * @param {string} username
 * @returns {Promise<boolean>} Whether the username is valid or not.
 */
async function validateUsername(username) {
    const resp = await fetch(`/Shruti/question22/accounts/username/validate?username=${username}`, {
        method: "GET"
    })

    const data = await resp.text()
    return data.trim() === "true"
}

usernameInput.addEventListener("input", function (ev) {
    clearTimeout(validator)
    /**@type {string} */
    const inputUsername = ev.target.value;
    // we don't fire the request as soon as the user writes something
    // rather let's pause for a moment, and if user stops typing, then 
    // we validate the username
    if (inputUsername == null || inputUsername.length === 0) {
        setUsernameContainerMessage("");
        return;
    }
    validator = setTimeout(function () {
        const errMsg = "username not available"
        const sucMsg = "username still available"

        validateUsername(inputUsername).then(function (isValid) {
            setUsernameContainerMessage(isValid ? sucMsg : errMsg)
        })
    }, 500)


});
