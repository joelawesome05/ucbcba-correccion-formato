import React, { Component } from 'react';
import PDF from 'react-pdf-js';
import PdfPreview from "./PdfPreview";

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
            <div className="form-group">
                <center><h4> Carátula </h4>
                    <label >Página:</label>
                    <input
                        name="coverPage"
                        type="number"
                        value={this.props.coverPage}
                        onChange={this.props.handleChange}
                    />
                    <br></br>
                </center>
                <center>
                    <PdfPreview
                        url={this.props.url}
                        pageStart={this.props.coverPage}
                        pageEnd={this.props.coverPage}
                    />
                </center>
            </div>
        )
    }
}

export default StepCover;
