const evtSource = new EventSource("/Shruti/q18/getStocks");
evtSource.onmessage = (event) => {
    const newElement = document.createElement("li");
    const eventList = document.getElementById("list");

    newElement.textContent = `message: ${event.data}`;
    eventList.appendChild(newElement);
};