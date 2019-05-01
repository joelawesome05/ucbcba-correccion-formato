import React, { Component } from 'react';
import PDF from 'react-pdf-js';
import PdfPreview from "./PdfPreview";
import { Link } from 'react-router-dom';

import "../style/PagesForm.css";

class StepCover extends Component {
    constructor(props) {
        super(props);
        this.state = {
            totalPages: null
        };
    }

    onDocumentComplete = (pages) => {
        this.setState({ totalPages: pages });
    }

    render() {
        if (this.props.currentStep !== 1) {
            return null
        }

        return (
            <div>
                <center><h4 className="tittle-wizard"> Carátula </h4> </center>
                <div className="row">
                    <div className="col-lg-4">
                        <div className="inputs-buttons">
                            <div className="leftForm">
                                <label className="myLabel">Página:</label>
                                <input
                                    name="coverPage"
                                    type="number"
                                    value={this.props.coverPage}
                                    onChange={this.props.handleChange}
                                />
                            </div>
                        </div>
                        <div className="next-previous-buttons">
                            <div className="leftForm">
                                <Link to="/">
                                    <button
                                        className="btn btn-secondary button-previous"
                                        type="button" >
                                        &laquo; Atrás
                                    </button>
                                </Link>
                                <button
                                    className="btn btn-primary "
                                    type="button" onClick={this.props.nextStep} >
                                    Siguiente &raquo;
                                </button>
                            </div>
                        </div>


                    </div>
                    <div className="col-lg-8">
                        <div class="scrollable">
                            <center>
                                <PdfPreview
                                    url={this.props.url}
                                    pageStart={this.props.coverPage}
                                    pageEnd={this.props.coverPage}
                                />
                            </center>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default StepCover;
