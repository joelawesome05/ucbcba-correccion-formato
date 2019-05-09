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

        var inputs = null;
        if (this.props.noBiography) {
            inputs = (
                <fieldset disabled>
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
                </fieldset>
            );
        } else {
            inputs = (
                <div>
                    <div className="leftForm">
                        <label className="myLabel">Página inicial:</label>
                        <input
                            name="biographyPageStart"
                            type="number"
                            value={this.props.biographyPageStart}
                            onChange={this.props.handleChange}
                            min="1"
                            required
                        />
                    </div>
                    <div className="leftForm">
                        <label className="myLabel">Página final:</label>
                        <input
                            name="biographyPageEnd"
                            type="number"
                            value={this.props.biographyPageEnd}
                            onChange={this.props.handleChange}
                            min="1"
                            required
                        />
                    </div>
                </div>
            );
        };


        return (
            <div>
                <center><h4 className="tittle-wizard"> Bibliografía </h4> </center>
                <div className="row">
                    <div className="col-lg-4">
                        <div className="inputs-buttons">
                            <div className="custom-control custom-checkbox checkBoxForm">
                                <input type="checkbox" className="custom-control-input" id="customCheck2" checked={this.props.noBiography} onChange={() => { this.props.handleBibliography() }} />
                                <label className="custom-control-label" for="customCheck2">No tengo esta sección</label>
                            </div>
                            <form onSubmit={this.props.nextStep}>
                                {inputs}

                                <div className="next-previous-buttons">
                                    <div className="leftForm">
                                        <button
                                            className="btn btn-secondary button-previous"
                                            type="button" onClick={this.props.previousStep} >
                                            &laquo; Anterior
                                </button>
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
                                    pageStart={this.props.biographyPageStart}
                                    pageEnd={this.props.biographyPageEnd}
                                    active={this.props.noBiography}
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
