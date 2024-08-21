import { useState } from "react";
import "./updateForm.css"

export default function CityUpdaterForm({ onSave, city, onCancel }) {
    const [cityName, setCityName] = useState(city.cityName);

    console.log(city.cityName);

    const onSubmit = (e) => {
        e.preventDefault();
        return onSave({ cityName });
    };

    return (
        <form className="cityUpdateForm" onSubmit={onSubmit}>
            <div className="control">
                <label htmlFor="cityName">New city name:</label>
                <input
                    value={cityName}
                    onChange={(e) => setCityName(e.target.value)}
                    id="cityName"
                />
                <button type="submit">Update City</button>
                <button type="button" onClick={onCancel}>Cancel</button>
            </div>
        </form>
    )
}