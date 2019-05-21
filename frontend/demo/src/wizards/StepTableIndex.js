import React, { Component } from 'react';
import PdfPreview from "./PdfPreview";

class StepTableIndex extends Component {
    constructor(props) {
        super(props);
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
                            name="tableIndexStartPage"
                            type="number"
                        />
                    </div>
                    <div className="leftForm">
                        <label className="myLabel">Página final:</label>
                        <input
                            name="tableIndexEndPage"
                            type="number"
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
                            name="tableIndexStartPage"
                            type="number"
                            value={this.props.tableIndexStartPage}
                            onChange={this.props.handleChange}
                            min="1"
                            required
                        />
                    </div>
                    <div className="leftForm">
                        <label className="myLabel">Página final:</label>
                        <input
                            name="tableIndexEndPage"
                            type="number"
                            value={this.props.tableIndexEndPage}
                            onChange={this.props.handleChange}
                            min="1"
                            required
                        />
                    </div>
                </div>

            );
        };
        var errorMessage = null;
        if (!this.props.validInputs) {
            errorMessage = (<div>
                <center>
                    <p className="alert alert-danger myAlert" role="alert"> Por favor ingrese un rango de páginas válido </p>
                </center>
            </div>);
        }
        return (
            <div>
                <center><h4 className="tittle-wizard"> Índice de Tablas </h4> </center>
                <div className="row">
                    <div className="col-lg-4">
                        <div className="inputs-buttons">
                            <div className="custom-control custom-checkbox checkBoxForm">
                                <input type="checkbox" className="custom-control-input" id="customCheck1" onChange={() => { this.props.handleTableIndex() }} checked={this.props.noTableIndex} />
                                <label className="custom-control-label" htmlFor="customCheck1">No tengo esta sección</label>
                            </div>
                            <form onSubmit={this.props.nextStep}>
                                {inputs}
                                {errorMessage}
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
                                    pageStart={this.props.tableIndexStartPage}
                                    pageEnd={this.props.tableIndexEndPage}
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
