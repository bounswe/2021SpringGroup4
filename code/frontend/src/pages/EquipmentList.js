import React, { Component } from 'react'

export class EquipmentList extends Component {
    constructor(){
        super();
        this.state={
            data:[]
        };
    }

    fetchData(){
        fetch('http://3.67.188.187:8000/api/equipment/', { 
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
                <td>{eqp.creator}</td>
                <td>{eqp.title}</td>
                <td>{eqp.description}</td>
                <td>{eqp.location}</td>
            </tr>
        );

        return (
            <div>
                <table className="table table-bordered" >
                <thead>
                    <tr>
                        <th>Creator</th>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Location</th>
                    </tr>
                </thead>
                <tbody>
                    {rows}
                </tbody>
            </table>
                
            </div>
        )
    }
}

export default EquipmentList