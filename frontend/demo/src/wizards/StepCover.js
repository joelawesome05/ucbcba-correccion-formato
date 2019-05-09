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

        var inputs = null;
        if (this.props.noCoverPage) {
            inputs = (
                <fieldset disabled>
                    <div className="leftForm">
                        <label className="myLabel">Página:</label>
                        <input
                            name="coverPage"
                            type="number"
                            value={this.props.coverPage}
                            onChange={this.props.handleChange}
                        />
                    </div>
                </fieldset>
            );
        } else {
            inputs = (
                <div className="leftForm">
                    <label className="myLabel">Página:</label>
                    <input
                        name="coverPage"
                        type="number"
                        value={this.props.coverPage}
                        onChange={this.props.handleChange}
                        min="1"
                        required
                    />
                </div>
            );
        };

        return (
            <div>
                <center><h4 className="tittle-wizard"> Carátula </h4> </center>
                <div className="row">
                    <div className="col-lg-4">
                        <div className="inputs-buttons">
                            <div className="custom-control custom-checkbox checkBoxForm">
                                <input type="checkbox" className="custom-control-input" id="customCheck1" onChange={() => { this.props.handleCoverPage() }} checked={this.props.noCoverPage} />
                                <label className="custom-control-label" htmlFor="customCheck1">No tengo esta sección</label>
                            </div>
                            <form onSubmit={this.props.nextStep}>
                                {inputs}
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
                                            type="submit" >
                                            Siguiente &raquo;
                                    </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div className="col-lg-8">
                        <div className="scrollable">
                            <center>
                                <PdfPreview
                                    url={this.props.url}
                                    pageStart={this.props.coverPage}
                                    pageEnd={this.props.coverPage}
                                    active={this.props.noCoverPage}
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
