import { useEffect, useState } from "react"
import { useGetToken } from "../../Hook/Hook"
import { Navigate, useNavigate } from "react-router-dom"
import "./solar-watch.css"


function fetchDbData() {
    return fetch('/api/solar-watch', {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("jwtToken")
        }
    }).then(res => res.json());
}

export default function Solarwatch() {
    const [cityName, setCityName] = useState('');
    const [searchedCity, setSearchedCity] = useState(null);
    const [solarReports, setSolarReports] = useState([]);
    const [loading, setLoading] = useState(true);
    const token = useGetToken();
    const navigate = useNavigate()


    useEffect(() => {
        fetchDbData()
            .then(data => setSolarReports(data))
            .then(() => setLoading(false));
    }, [])


    const handleSelectChange = (e) => {
        setCityName(e.target.value);
        console.log(e.target.value);
        const selectedCity = solarReports.find(solarReport => solarReport.city === e.target.value)
        setSearchedCity(selectedCity)
    }

    const handleGoBack = () => {
        navigate("/")
    }


    if (!token) {
        alert('You do not have acces to this page, please log in to continue...')
        return <Navigate to="/user/login" />;
    }

    if (loading) {
        return <h1>Loading...</h1>
    }

    return (
        <div className="wrapper">
            <div className="selectdiv">
                <div className="selLabel">
                    <label htmlFor="city">Select city:</label>
                    <select id="city" value={cityName} onChange={handleSelectChange}>
                        <option value={""} >--Select city--</option>
                        {solarReports.map((solarReport, index) => (
                            <option key={index} value={solarReport.city} onChange={(e) => setCityName(e.target.value)}>{solarReport.city}{' on ' + solarReport.date}</option>
                        ))}
                    </select>
                </div>

                <button className="formBtn backBtn" onClick={handleGoBack} type="button">Back</button>
            </div>
            <div className="circle">
                {searchedCity ?
                    <div className="cityDiv">
                        <p className="city">{`In the City of ${searchedCity.city} on ${searchedCity.date}`}</p>
                        <p className="sunrise">{`The sun rises at: ${searchedCity.sunrise}`}</p>
                        <p className="sunset">{`The sun sets at: ${searchedCity.sunset}`}</p>
                    </div>
                    : ""}
            </div>
        </div>
    )
}

