


// JWT tokenini Cookie'den al
function getJwtToken() {
    const name = "jwt="; // JWT tokeninin adını buraya yazın, "=" işaretini ekleyerek doğrudan arayabilirsiniz
    const decodedCookie = decodeURIComponent(document.cookie);
    const cookieArray = decodedCookie.split(';');

    for (let i = 0; i < cookieArray.length; i++) {
        let cookie = cookieArray[i].trim();
        if (cookie.startsWith(name)) {
            return cookie.substring(name.length);
        }
    }
    return null; // JWT tokeni yoksa null döndür
}

// JWT tokenini al
const jwtToken = getJwtToken();

// Şimdi jwtToken değişkenini kullanabilirsiniz
if (jwtToken) {
    fetch('/api/v1/device/getLatestTenSensorData/' + deviceId, {method: 'GET', credentials: 'include'})
        .then(response => response.json())
        .then(data => {
            let times = data.map(item => new Date(item.createTime).toLocaleString()); // 'createTime' özelliğini kullanıyoruz
            let humidityValues = data.map(item => item.humidity);

            let sensorData = {
                labels: times,
                datasets: [{
                    label: 'Humidity Value',
                    data: humidityValues,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            };

            let ctx = document.getElementById('sensorChart').getContext('2d');
            new Chart(ctx, {
                type: 'line',
                data: sensorData,
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        });

    fetch(`/api/v1/device/getLatestNpkValues/${deviceId}`, {method: 'GET', credentials: 'include'})
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('dataBody');
            const tr = document.createElement('tr');

            for (const key in data) {
                const td = document.createElement('td');
                td.innerText = data[key];
                tr.appendChild(td);
            }

            tbody.appendChild(tr);
        });


} else {
    // JWT tokeni yoksa gerekli işlemleri yapabilirsiniz
}


// Settings Section
// Minimum Humidity
const minHumidityInput = document.getElementById('minHumidityInput');
const minHumidityValueSpan = document.getElementById('minHumidityValue');
minHumidityInput.oninput = function () {
    minHumidityValueSpan.innerText = this.value + '%';
}

// Maximum Humidity
const maxHumidityInput = document.getElementById('maxHumidityInput');
const maxHumidityValueSpan = document.getElementById('maxHumidityValue');
maxHumidityInput.oninput = function () {
    maxHumidityValueSpan.innerText = this.value + '%';
}

function saveSettings() {
    const maxHumidityValue = maxHumidityInput.value;
    const minHumidityValue = minHumidityInput.value;
    const wateringTypeValue = document.getElementById('wateringType').value;
    const growingTypeValue= document.getElementById('growingType').value;
    const descriptionValue = document.getElementById('description').value;
    const nameValue = document.getElementById('name').value;
    const checkbox = document.getElementById('onOff');

    let wateringTypeToSend = wateringTypeValue === 'DRIP' ? 0 : 1;
    let growingTypeToSend = growingTypeValue === 'GREENHOUSE' ? 0 : 1;

    // Endpointe deviceID ekledik
    fetch('/api/v1/device/' + deviceId, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: nameValue,
            description: descriptionValue,
            growingType: growingTypeToSend,
            wateringType: wateringTypeToSend,
            offWatering: checkbox.checked,
            startWateringHumidityThreshold: maxHumidityValue,
            stopWateringHumidityThreshold: minHumidityValue,

        })
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            alert('Ayarlar kaydedildi!');
        })
        .catch(error => {
            console.error('Hata:', error);
        });
}






// Settings Section ended



//Maps Section

var myOptions = {
    zoom: 8,
    center: new google.maps.LatLng(38.419200,27.128700),
    mapTypeId: google.maps.MapTypeId.ROADMAP
};

var map = new google.maps.Map(document.getElementById("map"), myOptions);

//Maps Section Ended