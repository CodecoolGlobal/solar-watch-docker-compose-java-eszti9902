import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import CityUpdaterForm from "../../Components/CityUpdaterForm/CityUpdaterForm";

function fetchCity(cityId) {
    return fetch(`/api/admin/solarwatch/${cityId}`, {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("jwtToken")
        }
    }).then((res) => res.json());
}

const updateCity = (cityId, cityName) => {
    return fetch(`/api/admin/solarwatch/edit/${cityId}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("jwtToken")
        },
        body: JSON.stringify({ cityName }),
    }).then(res => res.text())
};
console.log(localStorage.getItem("jwtToken"))

export default function SolarwatchEditor() {
    const [city, setCity] = useState(null);
    const { cityId } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        fetchCity(cityId)
            .then((city) => {
                setCity(city);
            });
    }, [cityId]);

    const handleUpdateCity = (updatedCity) => {
        updateCity(cityId, updatedCity.cityName)
            .then(() => {
                navigate("/admin/solarwatch");
            });
    };
    console.log(city);

    if (city === null) {
        return <div>Loading...</div>;
    }

    return (
        <CityUpdaterForm
            onSave={handleUpdateCity}
            city={city}
            onCancel={() => navigate("/admin/solarwatch")}
        />
    )
}