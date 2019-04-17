import React, { Component } from 'react';
import PDF from 'react-pdf-js';
import PdfPreview from "./PdfPreview";

class StepGeneralIndex extends Component {
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
        if (this.props.currentStep !== 2) {
            return null
        }

        return (
            <div className="form-group">
                <center><h4> Índice general </h4>
                    <label >Página inicial:</label>
                    <input
                        name="generalIndexPageStart"
                        type="number"
                        value={this.props.generalIndexPageStart}
                        onChange={this.props.handleChange}
                    />
                    <label >Página final:</label>
                    <input
                        name="generalIndexPageEnd"
                        type="number"
                        value={this.props.generalIndexPageEnd}
                        onChange={this.props.handleChange}
                    />
                    <br></br>
                </center>
                <center>
                    <PdfPreview
                        url={this.props.url}
                        pageStart={this.props.generalIndexPageStart}
                        pageEnd={this.props.generalIndexPageEnd}
                    />
                </center>
            </div>
        )
    }
}

export default StepGeneralIndex;
