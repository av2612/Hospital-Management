import React, { Component } from 'react';
import { EDDCalculation } from './EDDCalculation';
import '../UserProfile.css'

export class EDD extends Component {
    constructor(props) {
        super(props);
        this.state = {
            modalShow: false,
            userId: this.props.EDDData.userId,
            showAll: false
        }
    }

    setModalShow = (setModalState) => {
        this.setState(setModalState);
    }
    // ({ updateState, EDDData, props }) => {
    // const [state, setModalShow] = React.useState({ "modalShow": false, "userId": EDDData.userId });
    // let LMPList = [];
    // let IVFList = [];
    // let UltrasoundList = [];
    // let LMPJsx = [];
    // let UltrasoundJSX = [];
    // let IVFJsx = [];


    // // const handleClose = () => setShow(false);
    // // const handleShow = () => setShow(true);
    // if (EDDData.LMP !== undefined && EDDData.LMP != null) {
    //     Object.keys(EDDData.LMP).forEach(function (key) {
    //         if (EDDData.LMP[key].length > 0)
    //             LMPList.push(EDDData.LMP[key][EDDData.LMP[key].length - 1]);
    //     });
    // }
    // if (EDDData.IVF !== undefined && EDDData.IVF != null) {
    //     Object.keys(EDDData.IVF).forEach(function (key) {
    //         if (EDDData.IVF[key].length > 0)
    //             IVFList.push(EDDData.IVF[key][EDDData.IVF[key].length - 1]);
    //     });
    // }
    // if (EDDData.Ultrasound !== undefined && EDDData.Ultrasound != null) {
    //     Object.keys(EDDData.Ultrasound).forEach(function (key) {
    //         if (EDDData.Ultrasound[key].length > 0)
    //             UltrasoundList.push(EDDData.Ultrasound[key][EDDData.Ultrasound[key].length - 1]);
    //     });
    // }
    render() {
        let EDDData = this.props.EDDData;
        let listOfEDD = [];
        if (EDDData.eddMapList !== undefined && EDDData.eddMapList != null) {
            Object.keys(EDDData.eddMapList).forEach(function (key) {
                EDDData.eddMapList[key].map((edd) => {
                    return listOfEDD.push(edd);
                })
            });
        }
        
        return (<div>
            <table className="responstable">
                <tbody>
                    <tr>
                        <th>Gestational Type</th>
                        <th>Entry Time</th>
                        <th>Expected Delivery Date</th>
                        <th>Entered By</th>
                        <th>
                            {EDDData.isEnabled ?
                                <span className="add-button" onClick={() => this.setModalShow({ "modalShow": true, "refId": null })} />
                                : null}
                        </th>
                    </tr>
                    {EDDData.latestEDD ?
                        <>
                            <tr>
                                <td>{EDDData.latestEDD.gestationalType}</td>
                                <td>{EDDData.latestEDD.entryTime}</td>
                                <td>{EDDData.latestEDD.edd}</td>
                                <td>{EDDData.latestEDD.enteredBy.firstName}({EDDData.latestEDD.enteredBy.userRole})</td>
                                <td><span className="edit-button" onClick={() => { this.setModalShow({ "modalShow": true, "gestationalType": "LMP", "refId": EDDData.latestEDD.refId }) }} />
                                </td>
                            </tr>
                        </> : null
                    }

                    {this.state.showAll ? listOfEDD.map((edd) => {
                        return (<tr key={edd.id}>
                            <td>{edd.gestationalType}({edd.refId}){edd.isActive==="1"?"(*)":""}</td>
                            <td>{edd.entryTime}</td>
                            <td>{edd.edd}</td>
                            <td>{edd.enteredBy.firstName}({edd.enteredBy.userRole})</td>
                            {/* <span className="edit-button" onClick={() => { this.setModalShow({ "modalShow": true, "gestationalType": "LMP", "refId": EDDData.latestEDD.refId }) }} /> */}
                        </tr>);
                    }) : <></>}

                    {/* <tr>
                    <td rowSpan={LMPList.length < 1 ? 1 : LMPList.length}>LMP</td>
                    {LMPList.length > 0 ?
                        (<><td>{LMPList[0].entryTime}</td>
                            <td>{LMPList[0].edd}</td>
                            <td>{LMPList[0].enteredBy.firstName}({LMPList[0].enteredBy.userRole})</td>
                            <span className="edit-button" onClick={() =>{setModalShow({"modalShow": true, "gestationalType":"LMP", "refId": LMPList[0]['refId']})}}/>
                        </>) : (<><td></td><td></td><td></td></>)
                    }
                </tr>
                {LMPList.forEach((LMP, index) => {
                    if (index == 0) {
                        return;
                    }
                    else {
                        LMPJsx.push((<tr><td>{LMPList[index].entryTime}</td>
                            <td>{LMP.edd}</td>
                            <td>{LMP.enteredBy.firstName}({LMP.enteredBy.userRole})</td>
                            <span className="edit-button" onClick={() =>{setModalShow({"modalShow": true, "gestationalType":"LMP", "refId": LMPList[index]['refId']})}}/>
                        </tr>))
                    }
                })}
                {LMPJsx}
                <tr>
                    <td rowSpan={UltrasoundList.length < 1 ? 1 : UltrasoundList.length}>Ultrasound</td>
                    {UltrasoundList.length > 0 ?
                        (<><td>{UltrasoundList[0].entryTime}</td>
                            <td>{UltrasoundList[0].edd}</td>
                            <td>{UltrasoundList[0].enteredBy.firstName}({UltrasoundList[0].enteredBy.userRole})</td>
                            <span className="edit-button" onClick={() =>{setModalShow({"modalShow": true, "gestationalType":"Ultrasound", "refId": UltrasoundList[0]['refId']})}}/>
                        </>) : (<><td></td><td></td><td></td></>)
                    }
                </tr>
                {UltrasoundList.forEach((Ultrasound, index) => {
                    if (index == 0)
                        return;
                    else
                        UltrasoundJSX.push(<tr><td>{Ultrasound.entryTime}</td>
                            <td>{Ultrasound.edd}</td>
                            <td>{Ultrasound.enteredBy.firstName}({Ultrasound.enteredBy.userRole})</td>
                            <span className="edit-button" onClick={() =>{setModalShow({"modalShow": true, "gestationalType":"Ultrasound", "refId": Ultrasound['refId']})}}/>
                        </tr>)
                })}
                {UltrasoundJSX}
                <tr>
                    <td rowSpan={IVFList.length < 1 ? 1 : IVFList.length}>IVF</td>
                    {IVFList.length > 0 ?
                        (<><td>{IVFList[0].entryTime}</td>
                            <td>{IVFList[0].edd}</td>
                            <td>{IVFList[0].enteredBy.firstName}({IVFList[0].enteredBy.userRole})</td>
                            <span className="edit-button" onClick={() =>{setModalShow({"modalShow": true, "gestationalType":"IVF", "refId": IVFList[0]['refId']})}}/>
                        </>) : (<><td></td><td></td><td></td></>)
                    }
                </tr>
                {IVFList.forEach((IVF, index) => {
                    if (index == 0)
                        return;
                    else
                        IVFJsx.push(<tr><td>{IVF.entryTime}</td>
                            <td>{IVF.edd}</td>
                            <td>{IVF.enteredBy.firstName}({IVF.enteredBy.userRole})</td>
                            <span className="edit-button" onClick={() =>{setModalShow({"modalShow": true, "gestationalType":"IVF", "refId": IVF['refId']})}}/>
                        </tr>)
                })}
                {IVFJsx} */}
                </tbody>
            </table>
            <button style={{ "float": "right", "clear": "both", "marginRight": "1%", "marginBottom": "5px", "padding": "10px" }}
                onClick={() => { this.setState({ showAll: !this.state.showAll }) }}
            >{this.state.showAll ? "-Hide" : "+Show All"}</button>
            
            <EDDCalculation
                show={this.state.modalShow}
                data={this.state}
                onHide={() => this.setModalShow({ "modalShow": false, "refId": null, "userId": EDDData.userId, "gestationalType": null })}
            />
        </div>
        );
    }
}