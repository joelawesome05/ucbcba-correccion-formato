import React, { Component } from 'react';
import PDF from 'react-pdf-js';
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

        return (
            <div className="form-group">
                <center><h4> Índice de Tablas </h4>
                    <label >Página inicial:</label>
                    <input
                        name="tableIndexPageStart"
                        type="number"
                        value={this.props.tableIndexPageStart}
                        onChange={this.props.handleChange}
                    />
                    <label >Página final:</label>
                    <input
                        name="tableIndexPageEnd"
                        type="number"
                        value={this.props.tableIndexPageEnd}
                        onChange={this.props.handleChange}
                    />
                    <br></br>
                </center>
                <center>
                    <PdfPreview
                        url={this.props.url}
                        pageStart={this.props.tableIndexPageStart}
                        pageEnd={this.props.tableIndexPageEnd}
                    />
                </center>
            </div>
        )
    }
}

export default StepTableIndex;
