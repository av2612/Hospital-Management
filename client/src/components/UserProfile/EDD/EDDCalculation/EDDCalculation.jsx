import React, { Component } from 'react';

import Modal from 'react-bootstrap/Modal'
import ModalHeader from 'react-bootstrap/ModalHeader'
import axios from 'axios';
import { baseApiUrl } from '../../../../actions/constant';
import './EDDCalculation.css';


export class EDDCalculation extends Component {
    constructor(props) {
        super(props)
        this.state = {
            userId: props.data.userId,
            gestationalInputData: {},
            gestationalType: "LMP",
            responseEdd: null
        }
    }

    handleGestationalDataChange = (event) => {
        this.setState({ gestationalInputData: { ...this.state.gestationalInputData, [event.target.name]: event.target.value } });
    }

    handleChange = (event) => {
        this.setState({ [event.target.name]: event.target.value });
    }

    submitHandler = (event) => {
        event.preventDefault();
        
        this.props.data.refId ? this.putEDDData() : this.postEDDData();
    }

    postEDDData = () => {
        console.log(this.state);
        return new Promise((resolve) => {
            axios.post(baseApiUrl + "users/edd", this.state)
                .then(response => {
                    if (response.status === 200) {
                        this.setState({ responseEdd: response.data });
                    }
                    else {
                        alert("Something went wrong");
                    }
                    resolve(response.data);
                }
                ).catch(error => {
                    if (error.response != null && error.response.status === 403) {
                        alert(error.response.data);
                    }
                    else {
                        alert("Something went wrong. We are logging you out.");
                    }
                });
        });
    }

    putEDDData = () => {
        console.log(this.state);
        let payload = this.state;
        payload.refId = this.props.data.refId;
        return new Promise((resolve) => {
            axios.post(baseApiUrl + "users/edd", payload)
                .then(response => {
                    if (response.status === 200) {
                        this.setState({ responseEdd: response.data });
                    }
                    else {
                        alert("Something went wrong");
                        this.props.logout();
                    }
                    resolve(response.data);
                }
                ).catch(error => {
                    if (error.response != null && error.response.status === 403) {
                        alert(error.response.data);
                        this.props.logOut(this.props);
                    }
                    else {
                        alert("Something went wrong. We are logging you out.");
                        this.props.logOut(this.props);
                    }
                });
        });
    }

    labelMapping = {
        "LMP": "First Day Of LMP",
        "Ultrasound": "Date of Ultrasound",
        "IVF": "Date of Transfer"
    }

    inputNameMapping = {
        "LMP": "LMPDate",
        "Ultrasound": "ultrasoundDate",
        "IVF": "dateOfTransfer"
    }


    render() {


        let dynamicField = null;
        switch (this.state.gestationalType) {
            case "LMP":
                break;
            case "IVF":
                break;
            case "Ultrasound":
                dynamicField = (
                    <>
                    <label className="label-edd">Gestational Age in Days</label>
                    <input type="text" className="edd-input" name="gestationalAge" onChange={(event) => this.handleGestationalDataChange(event)} />
                </>
                )
                    ;
                break;
            default:
                break;
        }
        return (
            <Modal
                {...this.props}
                size="lg"
                centered
            >

                <ModalHeader onClick={() => { this.setState({ responseEdd: null }); window.location.reload(); }} closeButton style={{ "float": "right" }} />
                {this.state.responseEdd ? <div>Expected Delivery date is {this.state.responseEdd.edd}</div>
                    :
                    <>
                        <h3 className="header-edd"> EDD Calculator</h3>
                        <form className="form-edd" onSubmit={(event) => this.submitHandler(event)}>
                            <label className="label-edd">Gestational Type</label>
                            <select name="gestationalType" value={this.state.gestationalType} onChange={(event) => this.handleChange(event)}>
                                <option value="LMP">LMP</option>
                                <option value="IVF">IVF</option>
                                <option value="Ultrasound">Ultrasound</option>
                            </select>
                            <label className="label-edd">{this.labelMapping[this.state.gestationalType]}</label>
                            <input type="date" className="edd-input" name={this.inputNameMapping[this.state.gestationalType]} onChange={(event) => this.handleGestationalDataChange(event)} />
                            {dynamicField}
                            <hr />
                            <input type="submit" value="Calculate EDD" />
                        </form>
                    </>
                }
            </Modal>
        );
    }
}
