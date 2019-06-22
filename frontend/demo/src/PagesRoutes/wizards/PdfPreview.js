import React, { Component } from 'react';
import { Document, Page } from 'react-pdf/dist/entry.webpack';
import { pdfjs } from 'react-pdf';
pdfjs.GlobalWorkerOptions.workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.js`;

class PdfPreview extends Component {

    constructor(props) {
        super(props);
        this.state = {
            numPages: null
        };
    }

    onDocumentLoadSuccess = ({ numPages }) => {
        this.setState({ numPages });
    }


    render() {
        if (this.props.active) {
            return (<center>
                <p> No tengo esta sección </p>
            </center>)
        } else {
            if (!this.props.pageStart || !this.props.pageEnd) {
                return (
                    <center>
                        <p> Esperando por un rango de páginas </p>
                    </center>
                )
            }
        };

        if (this.state.numPages != null) {
            if ((this.props.pageStart > this.props.pageEnd) || (this.props.pageStart < 1) || (this.props.pageEnd > this.state.numPages)) {
                return (
                    <center>
                        <p> Rango de páginas inválido </p>
                    </center>
                )
            }
        }

        var pdfPages = [];
        for (var i = this.props.pageStart; i <= this.props.pageEnd; i++) {
            pdfPages.push(
                <Page pageNumber={i} scale={0.35} key={i} className="pagesPDf" loading={"Cargando PDF..."} />);
        }
        return (
            <center>
                <Document
                    file={this.props.url}
                    onLoadSuccess={this.onDocumentLoadSuccess}
                    loading={"Cargando PDF..."}
                >
                    {pdfPages}
                </Document>
            </center>
        )
    }

}

export default PdfPreview;
