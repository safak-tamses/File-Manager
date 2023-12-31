import React, { Component } from "react";
import Dropzone from "react-dropzone";
import UploadService from "../Service/UploadFilesService";
import Modal from "react-modal";

export default class UploadFilesComponent extends Component {
  constructor(props) {
    super(props);
    this.upload = this.upload.bind(this);
    this.onDrop = this.onDrop.bind(this);
    this.showFileDetails = this.showFileDetails.bind(this);
    this.downloadFile = this.downloadFile.bind(this);
    this.updateFile = this.updateFile.bind(this);

    this.state = {
      selectedFiles: undefined,
      currentFile: undefined,
      progress: 0,
      message: "",
      fileInfos: [],
      isModalOpen: false,
      selectedFileDetails: null,
      errorMessage: ""
    };
  }

  componentDidMount() {
    UploadService.getFiles().then((response) => {
      const fileInfos = response.data.data;
      this.setState({
        fileInfos: fileInfos,
      });
    });
  }

  upload() {
    const { selectedFiles } = this.state;

    if (!selectedFiles || selectedFiles.length === 0) {
      this.setState({
        message: "Please select a file.",
      });
      return;
    }

    const formData = new FormData();
    formData.append("file", selectedFiles[0]);

    UploadService.upload(formData, (event) => {
      this.setState({
        progress: Math.round((100 * event.loaded) / event.total),
      });
    })
      .then((response) => {
        console.log("x ", response.data)
        if(response.data.errorMessage !== null){
          this.setState({
            errorMessage: response.data.errorMessage,
          })
        }
        if(response.data.data.message === 'File saved successfully.'){
          window.location.reload();
        }
      })
      .catch((error) => {
        this.setState({
          progress: 0,
          message: "Could not upload the file!",
          currentFile: undefined,
        });
        console.error("File upload error:", error);
      });

    this.setState({
      selectedFiles: undefined,
    });
  }

  onDrop(files) {
    if (files.length > 0) {
      this.setState({ selectedFiles: files });
    }
  }

  showFileDetails(fileId) {
    UploadService.getFileDetails(fileId)
      .then((response) => {
        this.setState({
          isModalOpen: true,
          selectedFileDetails: response.data.data,
        });
      })
      .catch((error) => {
        console.error("Error fetching file details:", error);
      });
  }

  downloadFile(fileId, fileName) {
    UploadService.downloadFile(fileId, fileName)
      .then(() => {})
      .catch((error) => {
        console.error("Error fetching file details:", error);
      });
  }

  updateFile(fileId) {
    const { selectedFiles } = this.state;

    if (!selectedFiles || selectedFiles.length === 0) {
      this.setState({
        message: "Please select a file.",
      });
      return;
    }

    const formData = new FormData();
    formData.append("file", selectedFiles[0]);

    UploadService.update(formData, fileId, (event) => {
      this.setState({
        progress: Math.round((100 * event.loaded) / event.total),
      });
    })
      .then((response) => {
        this.setState({
          message: response.data.message,
        });
        window.location.reload();
      })
      .catch((error) => {
        this.setState({
          progress: 0,
          message: "Could not update the file!",
          currentFile: undefined,
        });
        console.error("File update error:", error);
      });

    this.setState({
      selectedFiles: undefined,
    });
  }

  deleteFile(fileId) {
    UploadService.delete(fileId)
      .then((response) => {
        this.setState({
          message: response.data.message,
        });
        window.location.reload();
      })
      .catch((error) => {
        console.error("Could not update the file!", error);
      });
  }

  renderFileDetailsModal() {
    const { isModalOpen, selectedFileDetails } = this.state;

    return (
      <Modal isOpen={isModalOpen}>
        <div className="relative">
          <button
            className="absolute top-2 right-2 text-gray-600 hover:text-gray-800"
            onClick={() => this.setState({ isModalOpen: false })}
          >
            <svg
              className="h-6 w-6 fill-current"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path d="M12.7071 10.0001L16.3638 6.34336C16.7543 5.95282 16.7543 5.3199 16.3638 4.92936C15.9733 4.53883 15.3404 4.53883 14.9499 4.92936L11.2932 8.5861L7.63642 4.92936C7.24588 4.53883 6.61296 4.53883 6.22243 4.92936C5.83189 5.3199 5.83189 5.95282 6.22243 6.34336L9.87915 10.0001L6.22243 13.6569C5.83189 14.0475 5.83189 14.6804 6.22243 15.0709C6.61296 15.4615 7.24588 15.4615 7.63642 15.0709L11.2932 11.4141L14.9499 15.0709C15.3404 15.4615 15.9733 15.4615 16.3638 15.0709C16.7543 14.6804 16.7543 14.0475 16.3638 13.6569L12.7071 10.0001Z" />
            </svg>
          </button>
          {selectedFileDetails && (
            <div className="bg-white rounded-lg shadow-lg p-6 mt-4">
              <h2 className="text-lg font-semibold mb-4">File Details</h2>
              <p className="mb-1">File Name: {selectedFileDetails.fileName}</p>
              <p className="mb-1">
                File Extension: {selectedFileDetails.fileExtension}
              </p>
              <p className="mb-1">Path: {selectedFileDetails.path}</p>
              <p className="mb-1">File Size: {selectedFileDetails.fileSize}</p>
              <p className="mb-1">
                Create Date: {selectedFileDetails.createDate}
              </p>
              <p className="mb-1">
                Update Date: {selectedFileDetails.updateDate}
              </p>
              <p className="mb-1">User Name: {selectedFileDetails.userName}</p>
              <button
                className="px-4 py-2 mt-4 bg-gray-800 text-white rounded-lg hover:bg-gray-900"
                onClick={() => this.setState({ isModalOpen: false })}
              >
                Close
              </button>
            </div>
          )}
        </div>
      </Modal>
    );
  }

  render() {
    const { selectedFiles, currentFile, progress, message, fileInfos } =
      this.state;

    return (
      <div>
        <Dropzone onDrop={this.onDrop} multiple={false}>
          {({ getRootProps, getInputProps }) => (
            <section>
              <div {...getRootProps({ className: "dropzone" })}>
                <input {...getInputProps()} />
                {selectedFiles && selectedFiles[0].name ? (
                  <div className="selected-file">
                    {selectedFiles && selectedFiles[0].name}
                  </div>
                ) : (
                  <h4 className="text-black">
                    Drag and drop file here, or click to select file
                  </h4>
                )}
              </div>
              <aside className="selected-file-wrapper">
                <button
                  className="btn btn-secondary btn-lg btn-block"
                  disabled={!selectedFiles}
                  onClick={this.upload}
                >
                  Upload
                </button>
                <div className="h-8 w-full text-black mt-4">{this.state.errorMessage}</div>
              </aside>
            </section>
          )}
        </Dropzone>
        <br />
        {currentFile && (
          <div className="progress mb-3">
            <div
              className="progress-bar progress-bar-info progress-bar-striped"
              role="progressbar"
              aria-valuenow={progress}
              aria-valuemin="0"
              aria-valuemax="100"
              style={{ width: progress + "%" }}
            >
              {progress}%
            </div>
          </div>
        )}
        <div className="alert alert-light" role="alert">
          {message}
        </div>
        {fileInfos.length > 0 && (
          <div className="">
            <div className="text-black text-xl h-12 mb-2 flex justify-center items-center">
              Your existing files
            </div>
            <ul className="">
              {fileInfos.map((fileInfo) => (
                <div
                  className="flex flex-row bg-slate-200 justify-between border border-black mt-2 h-12 flex justify-center items-center"
                  key={fileInfo.id}
                >
                  <div className="w-4/6">
                    <span>{fileInfo.fileName}</span>
                  </div>
                  <button
                    className="w-1/6 h-3/4 bg-yellow-200 ring-2 hover:ring-4"
                    onClick={() => this.showFileDetails(fileInfo.id)}
                  >
                    File Details
                  </button>
                  <button
                    className="w-1/6 h-3/4 bg-green-200 ring-2 hover:ring-4 ml-2"
                    onClick={() =>
                      this.downloadFile(fileInfo.id, fileInfo.fileName)
                    }
                  >
                    Download file
                  </button>

                  <button
                    className="w-1/6 h-3/4 bg-blue-100 ring-2 hover:ring-4 ml-2"
                    onClick={() => this.updateFile(fileInfo.id)}
                  >
                    Update file
                  </button>

                  <button
                    className="w-1/6 h-3/4 bg-red-400 ring-2 hover:ring-4 ml-2 mr-2"
                    onClick={() => this.deleteFile(fileInfo.id)}
                  >
                    Delete file
                  </button>
                </div>
              ))}
            </ul>
          </div>
        )}
        {this.renderFileDetailsModal()} {/* Modal */}
      </div>
    );
  }
}
