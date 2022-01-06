import React, { Component } from "react";
import EquipmentCard from '../components/EquipmentCard'
import '../design/UserCards.css'



class Equipment extends Component {
    constructor(){
        super();
        this.state={
            data:[]
        };
    }

    fetchData(){
        fetch('http://localhost:8000/api/equipment/', { 
            method:'GET', } )

            
        .then(response=>response.json() )
        .then((data) =>{
            this.setState({
                data:data
            });
        } );

    }

    componentDidMount(){
        this.fetchData();
    }

    render() {
        const equipmentdata= this.state.data;
        const rows=equipmentdata.map((eqp)  => 
            <tr key={eqp.id}>
                
                <td>{eqp.title}</td>
                <td>{eqp.description}</td>
                <td>{eqp.location}</td>
                
                <td>{eqp.image_url}</td>
                <td>{eqp.contact}</td>
                <td>{eqp.sportType}</td>
                
                
            </tr>
            
        );

        return (
            <div className="events">
                { equipmentdata.map((eqp)  => 
                    <EquipmentCard title={eqp.title}  description= {eqp.description}   location ={eqp.location}  image_url= {eqp.image_url} contact={eqp.contact}  sportType={eqp.sportType} eqps={eqp}   />
                ) }
                




            </div>
        )
    }
}

export default Equipment;