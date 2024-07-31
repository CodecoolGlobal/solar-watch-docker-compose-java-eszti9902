import { Link } from "react-router-dom";

export default function SolarTable({ solarReports, onDelete }) {
    return (
        <div className="solarTable">
            <table>
                <thead>
                    <tr>
                        <th>City</th>
                        <th>Latitude</th>
                        <th>Longitude</th>
                        <th>Date</th>
                        <th>Sunrise</th>
                        <th>Sunset</th>
                        <th />
                    </tr>
                </thead>
                <tbody>
                    {solarReports.map((solarReport) => (
                        <tr>
                            <td>{solarReport.city}</td>
                            <td>{solarReport.lat}</td>
                            <td>{solarReport.lon}</td>
                            <td>{solarReport.date}</td>
                            <td>{solarReport.sunrise}</td>
                            <td>{solarReport.sunset}</td>
                            <td>
                                <Link to={`/admin/solarwatch/edit/${solarReport.cityId}`}>
                                    <button type="button">Update</button>
                                </Link>
                                <button type="button" onClick={() => onDelete(solarReport.cityId)}>
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div >
    )
}