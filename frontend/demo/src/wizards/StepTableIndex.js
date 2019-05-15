import React, { Component } from 'react';
import PdfPreview from "./PdfPreview";

class StepTableIndex extends Component {
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
        if (this.props.currentStep !== 4) {
            return null
        }

        var inputs = null;
        if (this.props.noTableIndex) {
            inputs = (
                <fieldset disabled>
                    <div className="leftForm">
                        <label className="myLabel">Página inicial:</label>
                        <input
                            name="tableIndexPageStart"
                            type="number"
                            value={this.props.tableIndexPageStart}
                            onChange={this.props.handleChange}
                        />
                    </div>
                    <div className="leftForm">
                        <label className="myLabel">Página final:</label>
                        <input
                            name="tableIndexPageEnd"
                            type="number"
                            value={this.props.tableIndexPageEnd}
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
                            name="tableIndexPageStart"
                            type="number"
                            value={this.props.tableIndexPageStart}
                            onChange={this.props.handleChange}
                            min="1"
                            required
                        />
                    </div>
                    <div className="leftForm">
                        <label className="myLabel">Página final:</label>
                        <input
                            name="tableIndexPageEnd"
                            type="number"
                            value={this.props.tableIndexPageEnd}
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
                <center><h4 className="tittle-wizard"> Índice de Tablas </h4> </center>
                <div className="row">
                    <div className="col-lg-4">
                        <div className="inputs-buttons">
                            <div className="custom-control custom-checkbox checkBoxForm">
                                <input type="checkbox" className="custom-control-input" id="customCheck1" onChange={() => { this.props.handleTableIndex() }} checked={this.props.noTableIndex} />
                                <label className="custom-control-label" for="customCheck1">No tengo esta sección</label>
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
                                    pageStart={this.props.tableIndexPageStart}
                                    pageEnd={this.props.tableIndexPageEnd}
                                    active={this.props.noTableIndex}
                                />
                            </center>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default StepTableIndex;
