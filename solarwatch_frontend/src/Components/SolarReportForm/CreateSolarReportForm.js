import { useState } from "react"
import "./solarReportForm.css"

export default function CreateSolarReportForm({ onSave }) {
    const [cityName, setCityName] = useState("");
    const [date, setDate] = useState("");

    const handleCreateNewSolarReport = (e) => {
        e.preventDefault();
        return onSave({ cityName, date })
    }

    return (
        <div className="reportform">
            <form onSubmit={handleCreateNewSolarReport}>
                <input
                    type="text"
                    value={cityName}
                    placeholder="City name"
                    required
                    onChange={(e) => setCityName(e.target.value)}
                />
                <input
                    type="text"
                    value={date}
                    placeholder="Date"
                    required
                    onChange={(e) => setDate(e.target.value)}
                />
                <button type="submit">Create new Solar Report</button>
            </form>
        </div>
    )
}