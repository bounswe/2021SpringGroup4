import React, { Component, useEffect, useState } from "react";
import EquipmentCard from '../components/EquipmentCard'
import { useLocation } from 'react-router-dom'

import '../design/UserCards.css'


function EquipmentSelect() {

    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const sportType = params.get('sportType') || '';
    const [equipmentData, setEquipmentData] = useState(null)
    
    useEffect(() => {
        console.log(sportType)
        fetch('http://3.67.188.187:8000/api/search/equipment/sport/', { 
            method:'POST', 
            headers:{
                'Content-type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify(
                {
                    "sportType": sportType
                }
            )
        } 
        ).then(res => {
            console.log(res);
            if(!res.ok){
                console.log(res);
                throw Error('could not fetch the data for that resource');
            }
            return res.json();
        }).then(data =>{
            console.log(sportType)
            console.log(data)
            setEquipmentData(data);
        } );

    }, [])


    return (
            <div className="events">
                {equipmentData && equipmentData.map((eqp)  => 
                    <EquipmentCard title={eqp.title}  description= {eqp.description}   location ={eqp.location}  image_url= {eqp.image_url} contact={eqp.contact}  sportType={eqp.sportType} eqps={eqp}   />
                ) }
            </div>
        )
}

export default EquipmentSelect;