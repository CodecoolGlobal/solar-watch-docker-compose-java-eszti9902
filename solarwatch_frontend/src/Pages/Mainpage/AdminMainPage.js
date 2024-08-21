import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import SolarTable from "../../Components/AdminCityTable/SolarTable";
import CreateSolarReportForm from "../../Components/SolarReportForm/CreateSolarReportForm";
import { useAuth } from "../../AuthProvider";
import "./adminpage.css"

function fetchDbData() {
    return fetch('/api/solar-watch', {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("jwtToken")
        }
    }).then(res => res.json()).then(data => {
        console.log(data)
        return data
    })
}

const deleteCity = (cityId) => {
    return fetch(`/api/admin/solarwatch/delete/${cityId}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("jwtToken")
        }
    }).then((res) =>
        res.text()
    );
};

function createSolarReport(solarReport) {
    return fetch("/api/admin/solarwatch/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("jwtToken")
        },
        body: JSON.stringify(solarReport)
    }).then(res => res.json())
}


export default function AdminMainPage() {
    const [solarReports, setSolarReports] = useState([]);
    const navigate = useNavigate();
    const [isCreationActive, setIsCreationActive] = useState(false);
    const { logout } = useAuth()


    useEffect(() => {
        fetchDbData()
            .then(data => {
                setSolarReports(Array.isArray(data) ? data : [])
            })
    }, [])

    const logOutUser = () => {
        console.log(localStorage.getItem("jwtToken"));
        localStorage.removeItem("jwtToken")
        localStorage.removeItem("user")
        logout()
        console.log(localStorage.getItem("jwtToken"));
        navigate("/");
    }

    const setIsCreationToActive = () => {
        setIsCreationActive(true);
    }

    const handleCreateNewSolarReport = (newSolarReport) => {
        createSolarReport(newSolarReport)
            .then(createdReport => {
                setSolarReports((prevReports) => [...prevReports, createdReport]);
                setIsCreationActive(false);
            })

    }

    const handleDeleteCity = (cityId) => {
        deleteCity(cityId);
        setSolarReports((solarReports) => {
            return solarReports.filter((solarReport) => solarReport.cityId !== cityId)
        })
    }

    const handleGoBack = () => {
        navigate("/")
    }

    return (
        <>
            <div className="buttons">
                <button className="createNew" type="button" onClick={setIsCreationToActive}>Create new Solar Report</button>
                <button className="back" onClick={handleGoBack} type="button">Back</button>
                <button className="logOut" onClick={logOutUser}>Log Out</button>
            </div>
            <SolarTable
                solarReports={solarReports}
                onDelete={handleDeleteCity}
            />
            {isCreationActive ? <CreateSolarReportForm onSave={handleCreateNewSolarReport} /> : ""}
        </>
    )
}