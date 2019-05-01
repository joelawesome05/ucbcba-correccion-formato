import React, { Component } from 'react';
import PDF from 'react-pdf-js';
import PdfPreview from "./PdfPreview";

class StepBiography extends Component {
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
        if (this.props.currentStep !== 5) {
            return null
        }

        return (
            <div>
                <center><h4 className="tittle-wizard"> Bibliografía </h4> </center>
                <div className="row">
                    <div className="col-lg-4">
                        <div className="inputs-buttons">
                            <div className="leftForm">
                                <label className="myLabel">Página inicial:</label>
                                <input
                                    name="biographyPageStart"
                                    type="number"
                                    value={this.props.biographyPageStart}
                                    onChange={this.props.handleChange}
                                />
                            </div>
                            <div className="leftForm">
                                <label className="myLabel">Página final:</label>
                                <input
                                    name="biographyPageEnd"
                                    type="number"
                                    value={this.props.biographyPageEnd}
                                    onChange={this.props.handleChange}
                                />
                            </div>
                        </div>
                        <div className="next-previous-buttons">
                            <div className="leftForm">
                                <button
                                    className="btn btn-secondary button-previous"
                                    type="button" onClick={this.props.previousStep} >
                                    &laquo; Anterior
                                </button>
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
                                    pageStart={this.props.biographyPageStart}
                                    pageEnd={this.props.biographyPageEnd}
                                />
                            </center>
                        </div>
                    </div>
                </div>

            </div>
        )
    }
}

export default StepBiography;
