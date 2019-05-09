import React, { Component } from 'react';
import PDF from 'react-pdf-js';
import PdfPreview from "./PdfPreview";

class StepAnnexed extends Component {
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
        if (this.props.currentStep !== 6) {
            return null
        }

        var inputs = null;
        if (this.props.noAnnexes) {
            inputs = (
                <fieldset disabled>
                    <div className="leftForm">
                        <label className="myLabel">Página inicial:</label>
                        <input
                            name="annexedPageStart"
                            type="number"
                            value={this.props.annexedPageStart}
                            onChange={this.props.handleChange}
                        />
                    </div>
                    <div className="leftForm">
                        <label className="myLabel">Página final:</label>
                        <input
                            name="annexedPageEnd"
                            type="number"
                            value={this.props.annexedPageEnd}
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
                            name="annexedPageStart"
                            type="number"
                            value={this.props.annexedPageStart}
                            onChange={this.props.handleChange}
                            min="1"
                            required
                        />
                    </div>
                    <div className="leftForm">
                        <label className="myLabel">Página final:</label>
                        <input
                            name="annexedPageEnd"
                            type="number"
                            value={this.props.annexedPageEnd}
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
                <center><h4 className="tittle-wizard"> Anexos </h4> </center>
                <div className="row">
                    <div className="col-lg-4">
                        <div className="inputs-buttons">
                            <div className="custom-control custom-checkbox checkBoxForm">
                                <input type="checkbox" className="custom-control-input" id="customCheck1" onChange={() => { this.props.handleAnnexes() }} checked={this.props.noAnnexes} />
                                <label className="custom-control-label" for="customCheck1">No tengo esta sección</label>
                            </div>
                            <form onSubmit={this.props.handleSubmit}>
                                {inputs}

                                <div className="next-previous-buttons">
                                    <div className="leftForm">
                                        <button
                                            className="btn btn-secondary button-previous"
                                            type="button" onClick={this.props.previousStep} >
                                            &laquo; Anterior
                                </button>
                                        <button
                                            className="btn btn-success "
                                            type="submit" >
                                            Enviar &raquo;
                                </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div className="col-lg-8">
                        <div class="scrollable">
                            <center>
                                <PdfPreview
                                    url={this.props.url}
                                    pageStart={this.props.annexedPageStart}
                                    pageEnd={this.props.annexedPageEnd}
                                    active={this.props.noAnnexes}
                                />
                            </center>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default StepAnnexed;