using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Infra.Exceptions
{
    public class InfraException : Exception
    {
        public InfraException() { }

        public InfraException(string message)
            : base(message) { }

        public InfraException(string message, Exception innerException)
            : base(message, innerException) { }
    }
}
